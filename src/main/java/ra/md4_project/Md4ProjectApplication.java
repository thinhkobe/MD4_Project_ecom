package ra.md4_project;

import org.apache.catalina.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ra.md4_project.model.entity.Role;
import ra.md4_project.model.entity.RoleName;
import ra.md4_project.model.entity.Users;
import ra.md4_project.repository.IUserRepository;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class Md4ProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(Md4ProjectApplication.class, args);
    }

}
