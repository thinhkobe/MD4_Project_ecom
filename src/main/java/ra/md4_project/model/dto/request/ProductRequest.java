package ra.md4_project.model.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import ra.md4_project.model.entity.Product;
import ra.md4_project.validator.UniqueValue;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

    @NotNull
    @Size(max = 100)
    @UniqueValue(entityClass = Product.class, fieldName = "productName")
    private String productName;

    private String description;

    @DecimalMin(value = "0.00")
    private double unitPrice;

    @Min(0)
    private Integer stockQuantity;

    @Size(max = 255)
    private String image;

    private Long categoryId;

}
