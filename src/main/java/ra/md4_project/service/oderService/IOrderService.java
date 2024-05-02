package ra.md4_project.service.oderService;

import ra.md4_project.exception.RequestError;
import ra.md4_project.model.dto.response.OrderResponse;

import java.util.List;

public interface IOrderService {
    List<OrderResponse>getHistoryList(Long userId);
    OrderResponse getOrderDetailBySerialNumber(String serialNumber);

    List<OrderResponse>getOrderDetailByOrderStatus(String orderStatus) throws RequestError;

}
