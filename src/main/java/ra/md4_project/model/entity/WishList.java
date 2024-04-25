package ra.md4_project.model.entity;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "wish_list")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wish_list_id")
    private Long wishListId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
