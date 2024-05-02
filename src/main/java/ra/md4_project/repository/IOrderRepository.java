package ra.md4_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.md4_project.model.entity.Order;
import ra.md4_project.model.entity.OrderDetail;
import ra.md4_project.model.entity.OrderStatus;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order,Long> {
   List<Order> getOrderByUserUserId(Long userId);
   Order getOrderBySerialNumber(String serialNumber);
   List<Order>findByStatus(OrderStatus oderStatus);
}
