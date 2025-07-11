package bank.consult.springboot.controller;


import bank.consult.springboot.dto.transactionDTO;
import bank.consult.springboot.model.transactionModel;
import bank.consult.springboot.service.transactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.OffsetDateTime;

@RestController
@RequestMapping("/deposit")
public class transactionsController {

    private final transactionService service;

    public transactionsController(transactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createTransaction(@Valid @RequestBody transactionDTO transactionrequest) {
        // Verifica se a transação não é no futuro
        if (transactionrequest.getDatetime().isAfter(OffsetDateTime.now()) || transactionrequest.getValue() <= 0){
            return ResponseEntity.unprocessableEntity().build();
        }
        // Cria o modelo de transação e chama o serviço para adicionar
        service.addTransaction(new transactionModel(transactionrequest.getValue(), transactionrequest.getDatetime()));

        // Retorna a resposta com status 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping ResponseEntity<Void> clearTransactions() {
        service.clearTransactions();
        return ResponseEntity.ok().build();
    }
}
