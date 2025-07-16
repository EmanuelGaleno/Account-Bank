package bank.consult.springboot.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "users", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column (name = "last_name")
    private String lastName;


    private String email;
    private String phone;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt= LocalDateTime.now();

    @OneToMany(mappedBy = "user")
    private List<AccountEntity> accounts;
}


