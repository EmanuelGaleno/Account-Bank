package bank.consult.springboot.entity;

import bank.consult.springboot.enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  // Relaciona com a tabela 'users' através da coluna 'user_id'
    private UserEntity user;  // Associa a conta ao usuário

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;  // Alterado para AccountType

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Construtor atualizado para aceitar AccountType
    public AccountEntity(UserEntity user, AccountType accountType, BigDecimal balance) {  // Alterado para AccountType
        this.user = user;
        this.accountType = accountType;
        this.balance = balance;
        this.createdAt = LocalDateTime.now();
    }
}
