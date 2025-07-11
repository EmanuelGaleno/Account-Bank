package bank.consult.springboot.controller;


import bank.consult.springboot.dto.statisticsDTO;
import bank.consult.springboot.service.transactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.DoubleSummaryStatistics;

@RestController
@RequestMapping("/statistics")
public class statisticsController {

    public final transactionService transactionService;

    public statisticsController(transactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity <statisticsDTO>getStatistics() {

        DoubleSummaryStatistics statistics = transactionService.getStatistics();
        return ResponseEntity.ok().body(new statisticsDTO(statistics));
    }

}
