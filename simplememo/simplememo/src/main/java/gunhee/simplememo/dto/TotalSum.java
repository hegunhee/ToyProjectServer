package gunhee.simplememo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TotalSum {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final BigDecimal incomeSum;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final BigDecimal expenseSum;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final BigDecimal totalSum;

    public TotalSum(BigDecimal incomeSum, BigDecimal expenseSum) {
        this.incomeSum = incomeSum;
        this.expenseSum = expenseSum;
        totalSum = incomeSum.subtract(expenseSum);
    }
}
