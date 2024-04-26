package ra.md4_project.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormUpdateUser {
    @NotNull(message = "Fullname is required")
    @NotBlank(message = "Fullname is required")
    @Size(max = 100, message = "Fullname cannot be longer than 100 characters")
    private String fullname;

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone is required")
    @NotNull(message = "Phone is required")
    @Pattern(regexp = "^\\+?[0-9]{8,15}$", message = "Invalid phone number")
    private String phone;

    @Size(max = 255, message = "Avatar URL cannot be longer than 255 characters")
    private String avatar;

}
