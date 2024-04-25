package ra.md4_project.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail {

    @EmbeddedId
    private OrderDetailId id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "order_quantity")
    private int orderQuantity;


}
