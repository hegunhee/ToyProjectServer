package gunhee.simplememo.dto.memo;

import com.fasterxml.jackson.annotation.JsonFormat;
import gunhee.simplememo.domain.memo.IncomeExpenseType;
import gunhee.simplememo.domain.memo.Memo;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class MemoResponse {

    private final Integer memoId;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime memoDate;

    private final IncomeExpenseType incomeExpenseType;

    private final String attribute;

    private final String asset;

    private final String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final BigDecimal price;

    public MemoResponse(Memo memo) {
        this.memoId = memo.getId();
        this.memoDate = memo.getMemoDate();
        this.incomeExpenseType = memo.getIncomeExpenseType();
        this.attribute = memo.getAttribute();
        this.asset = memo.getAsset();
        this.description = memo.getDescription();
        this.price = memo.getPrice();
    }
}
