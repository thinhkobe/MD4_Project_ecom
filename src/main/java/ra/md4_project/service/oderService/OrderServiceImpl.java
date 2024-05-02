package ra.md4_project.service.oderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.md4_project.exception.RequestError;
import ra.md4_project.model.dto.response.OrderDetailDTO;
import ra.md4_project.model.dto.response.OrderResponse;
import ra.md4_project.model.entity.Order;
import ra.md4_project.model.entity.OrderDetail;
import ra.md4_project.model.entity.OrderStatus;
import ra.md4_project.repository.IOrderDetailRepository;
import ra.md4_project.repository.IOrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IOrderDetailRepository orderDetailRepository;


    @Override
    public List<OrderResponse> getHistoryList(Long userId) {
        List<Order> orders = orderRepository.getOrderByUserUserId(userId);
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order : orders) {
            orderResponses.add(convertToOrderResponse(order));
        }
        return orderResponses;
    }

    public OrderDetailDTO convertToOrderDetailDTO(OrderDetail orderDetail) {
        return OrderDetailDTO.builder()
                .productName(orderDetail.getName())
                .price(orderDetail.getUnitPrice())
                .quantity(orderDetail.getOrderQuantity())
                .totalPrice(orderDetail.getUnitPrice() * orderDetail.getOrderQuantity())
                .build();
    }

    @Override
    public OrderResponse getOrderDetailBySerialNumber(String serialNumber) {
        Order order = orderRepository.getOrderBySerialNumber(serialNumber);
        return convertToOrderResponse(order);
    }

private OrderResponse convertToOrderResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setSerialNumber(order.getSerialNumber());
        orderResponse.setTotalPrice(order.getTotalPrice());
        orderResponse.setOrderStatus(order.getStatus());
        orderResponse.setCreatedAt(order.getCreatedAt());
        List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetailRepository.findByOrder(order)) {
            orderDetailDTOList.add(convertToOrderDetailDTO(orderDetail));
        }
        orderResponse.setOrderDetails(orderDetailDTOList);
        return orderResponse;
    }

    @Override
    public List<OrderResponse> getOrderDetailByOrderStatus(String orderStatus) throws RequestError {
        try {
            OrderStatus oderStatus = OrderStatus.valueOf(orderStatus);

            List<Order> orders = orderRepository.findByStatus(oderStatus);
            List<OrderResponse> orderResponses = new ArrayList<>();
            for (Order order : orders) {
                orderResponses.add(convertToOrderResponse(order));
            }
            return orderResponses;
        } catch (IllegalArgumentException e) {
            throw new RequestError("Invalid order status: " + orderStatus);
        }
    }

}
