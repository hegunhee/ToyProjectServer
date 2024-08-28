package gunhee.simplememo.dto.memo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.math.BigDecimal;

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
