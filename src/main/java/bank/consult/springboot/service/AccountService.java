package bank.consult.springboot.service;

import bank.consult.springboot.dto.AccountDTO;
import bank.consult.springboot.entity.AccountEntity;
import bank.consult.springboot.entity.UserEntity;
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

    // Aqui cria uma conta e um usuário
    public AccountDTO createAccount(String firstName, String lastName, String accountType, double initialBalance, String phone, String email) {

        // Verifica se o usuário já existe
        if (userExists(firstName, lastName)) {
            throw new IllegalArgumentException("Já existe um usuário com esse nome.");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setPhone(phone != null ? phone : "0000000000");  // Se o telefone não for passado, o valor padrão será "0000000000"
        userEntity.setEmail(email != null ? email : "default@domain.com");  // Se o email não for passado, o valor padrão será "default@domain.com"
        UserEntity savedUser = userRepository.save(userEntity);
        // Criação da conta para o usuário
        AccountEntity accountEntity = new AccountEntity(
                savedUser,  // Associando o usuário à conta
                accountType,  // Tipo da conta (corrente, poupança, etc.)
                BigDecimal.valueOf(initialBalance)  // Saldo inicial
        );
        AccountEntity savedAccount = accountRepository.save(accountEntity);
        return new AccountDTO(
                savedAccount.getId(),
                savedAccount.getUser().getId(),
                savedAccount.getAccountType(),
                savedAccount.getBalance(),
                savedAccount.getCreatedAt(),
                "Conta criada com sucesso para " + savedUser.getFirstName() + " " + savedUser.getLastName() + " com o tipo de conta: " + accountType
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
