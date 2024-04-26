package ra.md4_project.model.entity;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "full_address", length = 255)
    private String fullAddress;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "receive_name", length = 50)
    private String receiveName;

}
