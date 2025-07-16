package bank.consult.springboot.repository;

import bank.consult.springboot.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // Metodo para buscar usuarios por nome e sobrenome
    List<UserEntity> findByFirstNameAndLastName(String firstName, String lastName);

    // Metodo para buscar usuario pelo primeiro nome e ID
    List<UserEntity> findByFirstNameAndId(String firstName, Long id);
}
