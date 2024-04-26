package ra.md4_project.model.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
 public  class OrderDetailDTO {
        private String productName;
        private int quantity;
        private double price;
        private double totalPrice;

}