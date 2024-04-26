package ra.md4_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.md4_project.model.entity.OrderDetail;

@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetail,Long> {
}
