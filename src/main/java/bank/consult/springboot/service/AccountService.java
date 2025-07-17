package bank.consult.springboot.service;

import bank.consult.springboot.dto.AccountDTO;
import bank.consult.springboot.entity.AccountEntity;
import bank.consult.springboot.entity.UserEntity;
import bank.consult.springboot.enums.AccountType;
import bank.consult.springboot.repository.AccountRepository;
import bank.consult.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    // Verifica se o user já existe no banco
    private boolean userExists(String firstName, String lastName) {
        List<UserEntity> existingUser = userRepository.findByFirstNameAndLastName(firstName, lastName);
        return !existingUser.isEmpty();
    }

    // Criação de uma conta e um usuário
    public AccountDTO createAccount(String firstName, String lastName, AccountType accountType, double initialBalance, String phone, String email) {
        if (userExists(firstName, lastName)) {
            throw new IllegalArgumentException("Já existe um usuário com esse nome.");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Saldo inicial não pode ser negativo.");
        }

        // Criação do usuário
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setEmail(email != null ? email : "defaultEmail@example.com");
        userEntity.setPhone(phone != null ? phone : "0000000000");
        UserEntity savedUser = userRepository.save(userEntity);
        // Criando a conta com o tipo de conta baseado no enum
        AccountEntity accountEntity = new AccountEntity(
                savedUser,
                accountType,  // Usando o tipo do enum
                BigDecimal.valueOf(initialBalance)
        );
        AccountEntity savedAccount = accountRepository.save(accountEntity);
        return new AccountDTO(
                savedAccount.getId(),
                savedAccount.getUser().getId(),
                savedAccount.getAccountType().name(),  // Convertendo enum para string
                savedAccount.getBalance(),
                savedAccount.getCreatedAt(),
                "Conta criada com sucesso para " + savedAccount.getUser().getFirstName() + " com tipo de conta: " + savedAccount.getAccountType().name()
        );
    }

    // Aqui busca usuario pelo nome, sobrenome e ID com camel sensitive
    public UserEntity getUserByNameAndLastNameAndId(String firstName, String lastName, Long id) {
        List<UserEntity> users = userRepository.findByFirstNameAndLastNameAndId(firstName, lastName, id);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }
}
