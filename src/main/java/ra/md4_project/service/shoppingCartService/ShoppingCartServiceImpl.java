package ra.md4_project.service.shoppingCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.md4_project.exception.DataNotFound;
import ra.md4_project.exception.RequestError;
import ra.md4_project.model.dto.request.AddShoppingCartRequest;
import ra.md4_project.model.dto.request.OrderRequest;
import ra.md4_project.model.dto.response.OrderDetailDTO;
import ra.md4_project.model.dto.response.OrderResponse;
import ra.md4_project.model.dto.response.ShoppingCartResponse;
import ra.md4_project.model.entity.*;
import ra.md4_project.repository.IOrderDetailRepository;
import ra.md4_project.repository.IOrderRepository;
import ra.md4_project.repository.IProductRepository;
import ra.md4_project.repository.IShoppingCartRepository;
import ra.md4_project.service.productService.IProductService;
import ra.md4_project.service.userService.IUserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ShoppingCartServiceImpl implements IShopPingCartService {
    @Autowired
    private IShoppingCartRepository shoppingCartRepository;
    @Autowired
    private IUserService userService;
    @Autowired
    private IProductService productService;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IOrderDetailRepository orderDetailRepository;


    @Override
    public List<ShoppingCartResponse> getAllShoppingCartByUserId(Long userId) {
        List<ShoppingCart> cartList = shoppingCartRepository.findAllByUserUserId(userId);
        List<ShoppingCartResponse> cartResponseList = new ArrayList<>();
        for (ShoppingCart shoppingCart : cartList) {
            cartResponseList.add(mapToShoppingCartResponse(shoppingCart));
        }
        return cartResponseList;
    }

    @Override
    public ShoppingCartResponse doAddShoppingCart(AddShoppingCartRequest shopPingCartResponse, Long userId) throws DataNotFound, RequestError {
        ShoppingCart shoppingCart = new ShoppingCart();
        Product product = productService.findById(shopPingCartResponse.getProductId());
        shoppingCart.setUser(userService.findById(userId));
        shoppingCart.setProduct(product);
        if (product.getStockQuantity() < shopPingCartResponse.getQuantity()) {
            throw new RequestError("product don't have enough quantity");
        }
        shoppingCart.setOrderQuantity(shopPingCartResponse.getQuantity());
        shoppingCartRepository.save(shoppingCart);

        return mapToShoppingCartResponse(shoppingCart);
    }

    @Override
    public ShoppingCartResponse editProductQuantity(Long cartId, Integer newquantity) throws DataNotFound {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId).orElseThrow(() ->
                new DataNotFound("Cart ID not found"));
        shoppingCart.setOrderQuantity(newquantity);
        shoppingCartRepository.save(shoppingCart);

        return mapToShoppingCartResponse(shoppingCart);
    }

    @Override
    public ShoppingCartResponse deleteCart(Long cartId) throws DataNotFound {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId).orElseThrow(() ->
                new DataNotFound("Cart id not found"));
        shoppingCartRepository.delete(shoppingCart);

        return mapToShoppingCartResponse(shoppingCart);
    }

    private ShoppingCartResponse mapToShoppingCartResponse(ShoppingCart shoppingCart) {
        ShoppingCartResponse cartResponse = new ShoppingCartResponse();
        cartResponse.setProductName(shoppingCart.getProduct().getProductName());
        cartResponse.setProductPrice(shoppingCart.getProduct().getUnitPrice());
        cartResponse.setQuantity(shoppingCart.getOrderQuantity());
        cartResponse.setImage(shoppingCart.getProduct().getImage());
        return cartResponse;
    }


    @Override
    public ShoppingCartResponse deleteAllCartByUserLoginId(Long userId) throws DataNotFound {
        List<ShoppingCart> cartList = shoppingCartRepository.findAllByUserUserId(userId);
        if (cartList.isEmpty()) {
            throw new DataNotFound("no products in the cart");
        } else {
            shoppingCartRepository.deleteAll(cartList);
        }
        return null;
    }

    @Override
    public OrderResponse checkOut(Long userId, OrderRequest orderRequest) throws DataNotFound, RequestError {
        List<ShoppingCart> cartList = new ArrayList<>();
        for (Long shoppingCartId : orderRequest.getShoppingCartId()) {
            ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElseThrow(() -> new DataNotFound("Cart Id not Found"));
            if (shoppingCart.getProduct().getStockQuantity() < shoppingCart.getOrderQuantity()) {
                throw new RequestError("Stock over");
            }
            cartList.add(shoppingCart);
        }
        if (cartList.isEmpty()) {
            throw new DataNotFound("shopping cart is empty");
        }

        LocalDateTime now = LocalDateTime.now();
        String serialNumber = UUID.randomUUID().toString();

        Order newOrder = new Order();
        newOrder.setUser(userService.findById(userId));
        newOrder.setNote(orderRequest.getNote());
        newOrder.setCreatedAt(now);
        newOrder.setReceiveAddress(orderRequest.getReceiveAddress());
        newOrder.setReceivePhone(orderRequest.getReceivePhone());
        newOrder.setSerialNumber(serialNumber);
        newOrder.setStatus(OrderStatus.WAITING);

        double totalPrice = 0;
        for (ShoppingCart cart : cartList) {
            totalPrice += cart.getOrderQuantity() * cart.getProduct().getStockQuantity();
        }
        newOrder.setTotalPrice(totalPrice);
        Long oderId = orderRepository.save(newOrder).getOrderId();

        List<OrderDetail> orderDetailList = new ArrayList<>();
        List<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();
        for (ShoppingCart cart : cartList) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(new OrderDetailId(oderId, cart.getProduct().getProductId()));
            orderDetail.setName(cart.getProduct().getProductName());
            orderDetail.setProduct(cart.getProduct());
            Product product = cart.getProduct();
            product.setStockQuantity(cart.getProduct().getStockQuantity() - cart.getOrderQuantity());
            productRepository.save(product);
            orderDetail.setOrderQuantity(cart.getOrderQuantity());
            orderDetail.setOrder(newOrder);
            orderDetail.setUnitPrice(cart.getProduct().getUnitPrice());
            orderDetailList.add(orderDetail);
            orderDetailRepository.save(orderDetail);
            shoppingCartRepository.delete(cart);
            orderDetailDTOS.add(convertToOrderDetaiDTO(orderDetail));
        }
        return new OrderResponse(serialNumber, totalPrice, OrderStatus.CONFIRM, LocalDateTime.now(), orderDetailDTOS);

    }

    public OrderDetailDTO convertToOrderDetaiDTO(OrderDetail orderDetail) {
        return OrderDetailDTO.builder()
                .productName(orderDetail.getName())
                .price(orderDetail.getUnitPrice())
                .quantity(orderDetail.getOrderQuantity())
                .totalPrice(orderDetail.getUnitPrice() * orderDetail.getOrderQuantity())
                .build();
    }


}
