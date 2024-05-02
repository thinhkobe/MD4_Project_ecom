package ra.md4_project.service.userService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.md4_project.exception.DataExist;
import ra.md4_project.exception.DataNotFound;
import ra.md4_project.exception.Locked;
import ra.md4_project.exception.RequestError;
import ra.md4_project.model.dto.request.ChangePassWordDOT;
import ra.md4_project.model.dto.request.FormLogin;
import ra.md4_project.model.dto.request.FormRegister;
import ra.md4_project.model.dto.request.FormUpdateUser;
import ra.md4_project.model.dto.response.JWTResponese;
import ra.md4_project.model.dto.response.UserInformation;
import ra.md4_project.model.entity.Role;
import ra.md4_project.model.entity.RoleName;
import ra.md4_project.model.entity.Users;
import ra.md4_project.repository.IRoleRepository;
import ra.md4_project.repository.IUserRepository;
import ra.md4_project.security.jwt.JWTProvider;
import ra.md4_project.security.principle.UserDetailsCustom;
import ra.md4_project.service.userService.IUserService;

import java.util.*;
import java.util.stream.Collectors;

@Service

public class UserServiceImpl implements IUserService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IRoleRepository iRoleRepository;
    @Autowired
    private IUserRepository userRepository;


    @Override
    public boolean register(FormRegister formRegister) {

        Users user = Users.builder()
                .email(formRegister.getEmail())
                .fullname(formRegister.getFullName())
                .username(formRegister.getUsername())
                .password(passwordEncoder.encode(formRegister.getPassword()))
                .status(true)
                .createdAt(new Date())
                .phone(formRegister.getPhone())
                .build();
        if (formRegister.getRoleSet() != null && !formRegister.getRoleSet().isEmpty()) {
            Set<Role> roles = new HashSet<>();
            formRegister.getRoleSet().forEach(
                    r -> {
                        switch (r) {
                            case "ADMIN":
                                roles.add(iRoleRepository.findByRoleName(RoleName.ROLE_ADMIN).orElseThrow(() -> new NoSuchElementException("role not found")));
                            case "MANAGER":
                                roles.add(iRoleRepository.findByRoleName(RoleName.ROLE_MANAGER).orElseThrow(() -> new NoSuchElementException("role not found")));
                            case "USER":
                                roles.add(iRoleRepository.findByRoleName(RoleName.ROLE_USER).orElseThrow(() -> new NoSuchElementException("role not found")));
                            default:
                                roles.add(iRoleRepository.findByRoleName(RoleName.ROLE_USER).orElseThrow(() -> new NoSuchElementException("role not found")));
                        }
                    }
            );
            user.setRoleSet(roles);
        } else {
            // mac dinh la user
            Set<Role> roles = new HashSet<>();
            roles.add(iRoleRepository.findByRoleName(RoleName.ROLE_USER).orElseThrow(() -> new NoSuchElementException("role not found")));
            user.setRoleSet(roles);
        }
        userRepository.save(user);
        return true;
    }

    @Override
    public JWTResponese login(FormLogin formLogin) throws DataNotFound, Locked {
        //xac thuc thong qua
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(formLogin.getUsername(), formLogin.getPassword()));


        } catch (AuthenticationException e) {
            throw new DataNotFound("user name or password incorrect");
        }
        UserDetailsCustom userdetail = (UserDetailsCustom) authentication.getPrincipal();
        if (!userdetail.isStatus()) {
            throw new Locked("user inactive");
        }
        String accessToken = jwtProvider.gennerateAccessToken(userdetail);
        return JWTResponese.builder()
                .accessToken(accessToken)
                .fullName(userdetail.getFullName())
                .email(userdetail.getEmail())
                .roleSet(userdetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .status(userdetail.isStatus())
                .build();
    }

    @Override
    public Page<Users> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Users findById(Long id) throws DataNotFound {
        return userRepository.findById(id).orElseThrow(() -> new DataNotFound("User not found"));
    }

    @Override
    public Users changeUserStatusByUserId(Long userId) throws DataNotFound {
        Users users = findById(userId);
        users.setStatus(!users.isStatus());
        return userRepository.save(users);
    }

    @Override
    public List<Users> searchUserByName(String name) {
        return userRepository.findAllByUsernameContaining(name);
    }

    @Override
    public UserInformation getInfo(Long userId) throws DataNotFound {
        return convertToUserInformation(findById(userId));
    }

    public static UserInformation convertToUserInformation(Users user) {
        UserInformation userInfo = new UserInformation();
        userInfo.setUsername(user.getUsername());
        userInfo.setEmail(user.getEmail());
        userInfo.setFullname(user.getFullname());
        userInfo.setAvatar(user.getAvatar());
        userInfo.setPhone(user.getPhone());
        return userInfo;
    }

    @Override
    public UserInformation updateInfo(Long userId, FormUpdateUser formUpdateUser) throws DataNotFound, DataExist {
        Users users = findById(userId);
        String newPhone = formUpdateUser.getPhone();
        String newEmail = formUpdateUser.getEmail();

        // Kiểm tra và cập nhật số điện thoại
        if (!Objects.equals(newPhone, users.getPhone())) {
            if (userRepository.existsByPhone(newPhone)) {
                throw new DataExist("Phone number is available");
            }
            users.setPhone(newPhone);
        }

        // Kiểm tra và cập nhật email
        if (!Objects.equals(newEmail, users.getEmail())) {
            if (userRepository.existsByEmail(newEmail)) {
                throw new DataExist("Email is available");
            }
            users.setEmail(newEmail);
        }

        // Cập nhật các trường thông tin khác
        users.setFullname(formUpdateUser.getFullname());
        if (formUpdateUser.getAvatar() != null) {
            users.setAvatar(formUpdateUser.getAvatar());
        }

        userRepository.save(users);
        return convertToUserInformation(users);
    }

    @Override
    public boolean changePassWord(Long userid, ChangePassWordDOT changePassWordDOT) throws RequestError, DataNotFound {
        Users users = findById(userid);
        if (!passwordEncoder.matches(changePassWordDOT.getOldPassword(), users.getPassword())) {
            throw new RequestError(" old password is incorrect ");
        }


        if (!Objects.equals(changePassWordDOT.getOldPassword(), changePassWordDOT.getNewPassword())) {
            throw new RequestError("password and confirm password do not match");
        }
        users.setPassword(passwordEncoder.encode(changePassWordDOT.getNewPassword()));

        return true;
    }
}
