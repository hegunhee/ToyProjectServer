package gunhee.simplememo.dto.memo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import gunhee.simplememo.domain.IncomeExpenseType;
import gunhee.simplememo.domain.Memo;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class MemoRequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm")
    private final LocalDateTime memoDate;

    private final IncomeExpenseType incomeExpenseType;

    @NotEmpty(message = "분류는 빈값이 될 수 없습니다.")
    private final String attribute;

    @NotEmpty(message = "지불타입은 빈값이 될 수 없습니다.")
    private final String asset;

    private final String description;

    private final BigDecimal price;

    @JsonCreator
    public MemoRequest(
            @JsonProperty("memoOate")  LocalDateTime memoDate,
            @JsonProperty("incomeExpenseType")  IncomeExpenseType incomeExpenseType,
            @JsonProperty("attribute")  String attribute,
            @JsonProperty("asset")  String asset,
            @JsonProperty("description")  String description,
            @JsonProperty("price")  BigDecimal price) {
        this.memoDate = memoDate;
        this.incomeExpenseType = incomeExpenseType;
        this.attribute = attribute;
        this.asset = asset;
        this.description = description;
        this.price = price;
    }

    public Memo toEntity() {
        return new Memo(memoDate,incomeExpenseType,attribute,asset,description,price);
    }
}
