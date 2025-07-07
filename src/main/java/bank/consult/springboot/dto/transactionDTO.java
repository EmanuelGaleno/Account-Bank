package bank.consult.springboot.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public class transactionDTO {

    @NotNull //nao pode receber valores nulos
    @Min(0) // nao pode receber valores abaixo de zero
    private double value;

    @NotNull
    private OffsetDateTime datetime;

    public double getValue() {
        return value;
    }

    public OffsetDateTime getDatetime() {
        return datetime;
    }
}
