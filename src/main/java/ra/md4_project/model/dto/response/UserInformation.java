package ra.md4_project.model.dto.response;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserInformation {
    private String username;
    private String email;
    private String fullname;
    private String avatar;
    private String phone;

}
