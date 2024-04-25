package ra.md4_project.model.entity;


import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "serial_number", unique = true, nullable = false, length = 100)
    private String serialNumber;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users user;

    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "note", length = 100)
    private String note;

    @Column(name = "receive_name", length = 100)
    private String receiveName;

    @Column(name = "receive_address", length = 255)
    private String receiveAddress;

    @Column(name = "receive_phone", length = 15)
    private String receivePhone;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;

    @Temporal(TemporalType.DATE)
    @Column(name = "received_at")
    private Date receivedAt;



}
