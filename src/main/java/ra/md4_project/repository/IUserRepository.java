package ra.md4_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ra.md4_project.model.entity.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByUsername(String name);
    List<Users> findAllByUsernameContaining(String userName);

}
