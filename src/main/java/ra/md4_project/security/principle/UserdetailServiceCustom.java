package ra.md4_project.security.principle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ra.md4_project.model.entity.Users;
import ra.md4_project.repository.IUserRepository;

@Component
public class UserdetailServiceCustom implements UserDetailsService {
    @Autowired
    private IUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        return  UserDetailsCustom.build(user);
    }
}
