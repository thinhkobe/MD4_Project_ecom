package ra.md4_project.model.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChangePassWordDOT {

    private String oldPassword;

    private String newPassword;

    private String confirmPassword;


}
