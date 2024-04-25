package ra.md4_project.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", unique = true, nullable = false, length = 100)
    private String username;

    @Column(name = "email", unique = true, nullable = false, length = 255)
    private String email;

    @Column(name = "fullname", nullable = false, length = 100)
    private String fullname;

    @Column(name = "status", columnDefinition = "BIT DEFAULT 1")
    private boolean status;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "avatar", length = 255)
    private String avatar;

    @Column(name = "phone", unique = true, nullable = false, length = 15)
    private String phone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Address> addresses;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;

    @Temporal(TemporalType.DATE)
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date updatedAt;

    @Column(name = "is_deleted", columnDefinition = "BIT DEFAULT 1")
    private boolean isDeleted;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)

    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roleSet;


}
