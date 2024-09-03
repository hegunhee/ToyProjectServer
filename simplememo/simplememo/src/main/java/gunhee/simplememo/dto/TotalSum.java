package gunhee.simplememo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;

@Schema(description = "해당 년, 월에 따라서 지출, 수입, 전체 계산 값들이 들어가있는 DTO")
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
