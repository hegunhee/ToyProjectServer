package gunhee.simplememo.dto.memo;

import com.fasterxml.jackson.annotation.JsonFormat;
import gunhee.simplememo.domain.memo.MemosVO;
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

    public AttributeMemoSummaryResponse(String attribute, MemosVO memosVO) {
        this.totalPrice = memosVO.calculateSumByAttribute(attribute);
        this.attribute = attribute;
        this.memos = memosVO.toMemosResponses();
    }
}
