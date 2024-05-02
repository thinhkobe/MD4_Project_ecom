package ra.md4_project.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ra.md4_project.model.entity.Users;
import ra.md4_project.validator.UniqueValue;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FormRegister {
    private Long userId;

    @UniqueValue(entityClass = Users.class, fieldName = "username")
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 6, max = 100, message = "Username must be between 6 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username can only contain letters and numbers")
    private String username;

    @NotBlank(message = "Full name cannot be empty")
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    private String fullName;

    @UniqueValue(entityClass = Users.class, fieldName = "email")
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;

    @NotBlank(message = "password cannot be empty")
    private String password;

    @UniqueValue(entityClass = Users.class, fieldName = "phone")
    @NotBlank(message = "phone cannot be empty")
    private String phone;

    private Set<String> roleSet;
}
