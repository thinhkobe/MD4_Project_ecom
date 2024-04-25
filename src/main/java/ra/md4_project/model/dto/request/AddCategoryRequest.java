package ra.md4_project.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ra.md4_project.model.entity.Category;
import ra.md4_project.validator.UniqueValue;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddCategoryRequest {

    @NotBlank
    @NotNull
    @Size(max = 100)
    @UniqueValue(entityClass = Category.class,fieldName = "categoryName",message = "categoryName is exist")
    private String categoryName;

    @NotBlank
    @NotNull
    private String description;
}
