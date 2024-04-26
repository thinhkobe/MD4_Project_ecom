package ra.md4_project.model.dto.response;

import lombok.*;
import ra.md4_project.model.entity.Role;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JWTResponese {
    private final String type="Bearer";
    private String accessToken;
    private String fullName;
    private String email;
    private Set<String> roleSet;
    private boolean status;
}
