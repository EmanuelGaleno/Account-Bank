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
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    // Buscar contas associadas ao usuario pelo ID
    public List<AccountDTO> getAccountsByUserId(Long userId) {
        List<AccountEntity> accountEntities = accountRepository.findByUserId(userId);
        return accountEntities.stream()
                .map(accountEntity -> new AccountDTO(
                        accountEntity.getId(),
                        accountEntity.getUser().getId(),
                        accountEntity.getAccountType(),
                        accountEntity.getBalance(),
                        accountEntity.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    // Busca user pelo nome e sobre
    public List<UserEntity> getUserByNameAndLastName (String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName);
    }


    // Criando user
    public AccountDTO createAccount(String firstName, String lastName, String accountType, double initialBalance) {

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        // O email e telefone podem ser nulos, mas vou add outra validacao aqui
        userEntity.setEmail("email@domain.com");
        userEntity.setPhone("000000000");

        // Salvando o usuário no banco
        UserEntity savedUser = userRepository.save(userEntity);
        AccountEntity accountEntity = new AccountEntity(
                savedUser,
                accountType,
                BigDecimal.valueOf(initialBalance) // define saldo inicial
        );
        // Salvando a conta no banco
        AccountEntity savedAccount = accountRepository.save(accountEntity);
        return new AccountDTO(
                savedAccount.getId(),
                savedAccount.getUser().getId(),
                savedAccount.getAccountType(),
                savedAccount.getBalance(),
                savedAccount.getCreatedAt()
        );
    }

    // Metodo para buscar usuario pelo nome e ID
    public UserEntity getUserByNameAndId(String firstName, Long id) {
        List<UserEntity> users = userRepository.findByFirstNameAndId(firstName, id);
        if (users.isEmpty()) {
            return null; // Retorna null mas vou lançar uma excecao customizada
        }
        return users.get(0);  // Retorna o primeiro usuario encontrado
    }
}
