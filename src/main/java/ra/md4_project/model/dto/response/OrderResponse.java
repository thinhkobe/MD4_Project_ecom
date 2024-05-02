package ra.md4_project.model.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ra.md4_project.model.entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderResponse {
    private String serialNumber;
    private double totalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private List<OrderDetailDTO> orderDetails;
}
