package bank.consult.springboot.controller;

import bank.consult.springboot.dto.AccountDTO;
import bank.consult.springboot.entity.UserEntity;
import bank.consult.springboot.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // criar uma nova conta com user
    @PostMapping
    public AccountDTO createAccount(@RequestParam String firstName,
                                    @RequestParam String lastName,
                                    @RequestParam String accountType,
                                    @RequestParam double initialBalance) {
        return accountService.createAccount(firstName, lastName, accountType, initialBalance);
    }

    // Metodo para buscar contas de um usuario pelo nome e sobrenome
    @GetMapping
    public List<AccountDTO> getAccountsByUser(@RequestParam String firstName,
                                              @RequestParam String lastName) {
        List<UserEntity> users = accountService.getUserByNameAndLastName(firstName, lastName);
        if (users.isEmpty()) {
            return null;  // Aqui vou ajustar a resposta (ex: retorno de erro 404 ou uma mensagem mais bonitinha)
        }
        return accountService.getAccountsByUserId(users.get(0).getId());  // Pegando o primeiro user encontrado
    }

    // Metodo para buscar usuário pelo nome e ID
    @GetMapping("/user")
    public UserEntity getUserByNameAndId(@RequestParam String firstName, @RequestParam Long id) {
        return accountService.getUserByNameAndId(firstName, id);
    }
}
