package ra.md4_project.security.principle;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ra.md4_project.model.entity.Users;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDetailsCustom implements UserDetails {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String password;
    private boolean status;
    private Collection<? extends GrantedAuthority> authorities;
    public static UserDetailsCustom build(Users user){
        List<? extends GrantedAuthority> authorityList = user.getRoleSet().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName().name())).toList();
        return UserDetailsCustom.builder()
                .id(user.getUserId())
                .username(user.getUsername())
                .password(user.getPassword())
                .fullName(user.getFullname())
                .email(user.getEmail())
                .authorities(authorityList)
                .status(user.isStatus())
                .build();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}