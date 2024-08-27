package gunhee.simplememo.dto.memo;

import com.fasterxml.jackson.annotation.JsonFormat;
import gunhee.simplememo.domain.IncomeExpenseType;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class StaticsMemosResponse {

    private final IncomeExpenseType type;
    private final int year;
    private final int month;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final BigDecimal totalPrice;
    private final List<StaticsMemoDto> staticsMemos;

    public StaticsMemosResponse(IncomeExpenseType type, int year, int month, BigDecimal totalPrice, List<StaticsMemoDto> staticsMemos) {
        this.type = type;
        this.year = year;
        this.month = month;
        this.totalPrice = totalPrice;
        this.staticsMemos = staticsMemos;
    }
}
