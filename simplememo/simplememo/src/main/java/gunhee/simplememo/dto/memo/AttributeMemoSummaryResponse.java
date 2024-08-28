package gunhee.simplememo.dto.memo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;

@Schema(description = "특정 분류의 가계부들 DTO")
@Getter
public class AttributeMemoSummaryResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final BigDecimal totalPrice;
    private final String attribute;
    private final MemosResponse memos;

    public AttributeMemoSummaryResponse(String attribute, MemosResponse memos) {
        this.totalPrice = memos.calculateAttributeSum(attribute);
        this.attribute = attribute;
        this.memos = memos;
    }
}
