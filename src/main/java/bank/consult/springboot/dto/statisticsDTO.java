package bank.consult.springboot.dto;

import java.util.DoubleSummaryStatistics;

public class statisticsDTO {

    private long count;

    private double sum;

    private double avg;

    private double max;

    private double min;

    public statisticsDTO(DoubleSummaryStatistics stats) {
        this.count = stats.getCount();
        this.sum = stats.getSum();
        this.avg = stats.getAverage();
        this.max = stats.getMax();
        this.min = stats.getMin();
    }

    //isso aqui pode sair com loombok
    public long getCount() {
        return count;
    }
    public double getSum() {
        return sum;
    }
    public double getAvg() {
        return avg;
    }
    public double getMax() {
        return max;
    }
    public double getMin() {
        return min;
    }
}
