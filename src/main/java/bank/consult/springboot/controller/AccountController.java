package bank.consult.springboot.controller;


import bank.consult.springboot.dto.AccountDTO;
import bank.consult.springboot.entity.UserEntity;
import bank.consult.springboot.service.AccountService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // busca usuário pelo nome, sobrenome e ID
    @GetMapping("/user")
    public UserEntity getUserByNameAndId(@RequestParam @NotNull String firstName,
                                         @RequestParam @NotNull String lastName,
                                         @RequestParam Long id) {
        UserEntity user = accountService.getUserByNameAndLastNameAndId(firstName, lastName, id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Usuário não encontrado com o nome: " + firstName + ", sobrenome: " + lastName + " e ID: " + id);
        }
        return user;
    }

    // Método POST para criar uma conta com os parâmetros necessários
    @PostMapping
    public AccountDTO createAccount(@RequestParam @NotNull String firstName,
                                    @RequestParam @NotNull String lastName,
                                    @RequestParam @NotNull String accountType,
                                    @RequestParam @NotNull double initialBalance,
                                    @RequestParam(required = false) String phone,  // Parâmetro opcional
                                    @RequestParam(required = false) String email) {  // Parâmetro opcional
        try {
            return accountService.createAccount(firstName, lastName, accountType, initialBalance, phone, email);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
