package ra.md4_project.service.shoppingCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.md4_project.exception.DataNotFound;
import ra.md4_project.model.dto.request.AddShoppingCartRequest;
import ra.md4_project.model.dto.response.ShoppingCartResponse;
import ra.md4_project.model.entity.Product;
import ra.md4_project.model.entity.ShoppingCart;
import ra.md4_project.repository.IShoppingCartRepository;
import ra.md4_project.service.productService.IProductService;
import ra.md4_project.service.userService.IUserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements IShopPingCartService {
    @Autowired
    private IShoppingCartRepository iShoppingCartRepository;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IProductService iProductService;

    @Override
    public List<ShoppingCartResponse> getAllShoppingCartByUserId(Long userId) {
        List<ShoppingCart> cartList = iShoppingCartRepository.findAllByUserUserId(userId);
        List<ShoppingCartResponse> cartResponseList = new ArrayList<>();
        for (ShoppingCart shoppingCart : cartList) {
            ShoppingCartResponse cartResponse = new ShoppingCartResponse();
            cartResponse.setProductName(shoppingCart.getProduct().getProductName());
            cartResponse.setProductPrice(shoppingCart.getProduct().getUnitPrice());
            cartResponse.setQuantity(shoppingCart.getOrderQuantity());
            cartResponse.setImage(shoppingCart.getProduct().getImage());
            cartResponseList.add(cartResponse);
        }
        return cartResponseList;
    }

    @Override
    public ShoppingCartResponse doAddShoppingCart(AddShoppingCartRequest shopPingCartResponse, Long userId) throws DataNotFound {
        ShoppingCart shoppingCart = new ShoppingCart();
        Product product = iProductService.findById(shopPingCartResponse.getProductId());
        shoppingCart.setUser(iUserService.findById(userId));
        shoppingCart.setProduct(product);
        shoppingCart.setOrderQuantity(shopPingCartResponse.getQuantity());
        iShoppingCartRepository.save(shoppingCart);

        ShoppingCartResponse newShoppingCart = new ShoppingCartResponse();
        newShoppingCart.setProductName(product.getProductName());
        newShoppingCart.setImage(product.getImage());
        newShoppingCart.setQuantity(shopPingCartResponse.getQuantity());
        newShoppingCart.setProductPrice(product.getUnitPrice());

        return newShoppingCart;
    }
}
