package ra.md4_project.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "sku", unique = true, nullable = false, length = 100)
    private String sku;

    @Column(name = "product_name", unique = true, nullable = false, length = 100)
    private String productName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "stock_quantity", columnDefinition = "INT DEFAULT 0 CHECK (stock_quantity >= 0)")
    private Integer stockQuantity;

    @Column(name = "image", length = 255)
    private String image;

    @JoinColumn(name = "categoryId")
    @ManyToOne
    private Category category;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;

    @Temporal(TemporalType.DATE)
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date updatedAt;
    @Column(name = "status", columnDefinition = "BIT DEFAULT 1")
    private boolean status;
}
