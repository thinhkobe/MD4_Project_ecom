package ra.md4_project.model.dto.response;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ShoppingCartResponse {
  private   String productName;
  private  Integer quantity;
  private  String image;
  private BigDecimal productPrice;
}
