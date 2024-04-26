package ra.md4_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.md4_project.model.entity.ShoppingCart;

import java.util.List;

@Repository
public interface IShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {
    List<ShoppingCart>findAllByUserUserId(Long userId);
}
