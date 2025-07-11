package bank.consult.springboot.model;

import java.time.OffsetDateTime;



public class transactionModel {

    private double value;
    private OffsetDateTime datetime;

    public transactionModel(double value, OffsetDateTime datetime) {
        this.value = value;
        this.datetime = datetime;
    }









    //aqui pode ficar bem melhor ultilizando lombook
    // so usei os metodo get pq ja passei os atributos na classe do construtor
    public double getValue() {
        return value;
    }
    public OffsetDateTime getDatetime() {
        return datetime;
    }
}

