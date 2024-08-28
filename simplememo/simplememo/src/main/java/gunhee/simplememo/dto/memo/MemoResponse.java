package gunhee.simplememo.dto.memo;

import com.fasterxml.jackson.annotation.JsonFormat;
import gunhee.simplememo.domain.memo.IncomeExpenseType;
import gunhee.simplememo.domain.memo.Memo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "가계부 요청 DTO")
@Getter
public class MemoResponse {

    private final Integer memoId;

    @Schema(description = "가계부에 저장된 날짜",type = "LocalDateTime",example="yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime memoDate;

    @Schema(description = "수입/지출 타입",type = "String",example="INCOME")
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
