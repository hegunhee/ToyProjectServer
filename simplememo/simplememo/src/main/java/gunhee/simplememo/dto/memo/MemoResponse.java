package gunhee.simplememo.dto.memo;

import gunhee.simplememo.domain.IncomeExpenseType;
import gunhee.simplememo.domain.Memo;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class MemoResponse {

    private final Integer memoId;

    private final LocalDateTime memoDate;

    private final IncomeExpenseType incomeExpenseType;

    private final String attribute;

    private final String asset;

    private final String description;

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
