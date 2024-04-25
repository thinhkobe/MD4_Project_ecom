package ra.md4_project.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddShoppingCartRequest {
    @NotNull(message ="productId must not be null" )
    private Long productId;
    @NotNull(message ="quantity must not be null" )
    private Integer quantity;
}
