package ra.md4_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.md4_project.model.entity.Order;

@Repository
public interface IOrderRepository extends JpaRepository<Order,Long> {
}
