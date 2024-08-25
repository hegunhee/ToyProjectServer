package gunhee.simplememo.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TotalSum {

    private final BigDecimal incomeSum;
    private final BigDecimal expenseSum;
    private final BigDecimal totalSum;

    public TotalSum(BigDecimal incomeSum, BigDecimal expenseSum) {
        this.incomeSum = incomeSum;
        this.expenseSum = expenseSum;
        totalSum = incomeSum.subtract(expenseSum);
    }
}
