package bank.consult.springboot.repository;

import bank.consult.springboot.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
}
