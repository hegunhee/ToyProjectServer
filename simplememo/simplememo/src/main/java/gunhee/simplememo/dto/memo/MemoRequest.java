package gunhee.simplememo.dto.memo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import gunhee.simplememo.domain.memo.IncomeExpenseType;
import gunhee.simplememo.domain.memo.Memo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "가계부 등록DTO")
@Getter
public class MemoRequest {

    @Schema(description = "가계부에 저장된 날짜",type = "LocalDateTime",example="yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime memoDate;

    @Schema(description = "수입/지출 타입",type = "String",example="INCOME")
    private final IncomeExpenseType incomeExpenseType;

    @NotBlank(message = "분류는 빈값이 될 수 없습니다.")
    @Size(max = 10,message = "10글자 이상이면 안됩니다.")
    private final String attribute;

    @NotBlank(message = "지불타입은 빈값이 될 수 없습니다.")
    @Size(max = 10,message = "10글자 이상이면 안됩니다.")
    private final String asset;

    @Size(max = 50,message = "50글자 이상이면 안됩니다.")
    private final String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final BigDecimal price;

    @JsonCreator
    public MemoRequest(
            @JsonProperty("memoDate")  LocalDateTime memoDate,
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
