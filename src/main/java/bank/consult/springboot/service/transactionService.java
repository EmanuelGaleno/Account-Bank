package bank.consult.springboot.service;

import bank.consult.springboot.model.transactionModel;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.time.OffsetDateTime.now;

@Service
public class transactionService {

    // aqui testando a libary Queue
    private final Queue<transactionModel> transactions = new ConcurrentLinkedQueue<>();

    /**Metodo para adicionar uma transação à fila*/
    public  void addTransaction(transactionModel transaction) {
        transactions.add(transaction);  // Adicionando os valores vindo de model para a fila transaction criada a cima
    }

    //metodo para limpar as transacoes
    public void clearTransactions() {
        transactions.clear();
    }

    //metodo para pegar as transacoes num intervalo de 60s de primeira
    public DoubleSummaryStatistics getStatistics() {
        OffsetDateTime now = now();
        return transactions.stream()// usando stream pq eu gosto
                .filter(t -> t.getDatetime().isAfter(now.minusSeconds(60)))
                .mapToDouble(transactionModel::getValue)
                .summaryStatistics();
    }
}