package gunhee.simplememo.dto.memo;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class AttributeMemoSummaryResponse {

    private final BigDecimal totalPrice;
    private final String attribute;
    private final MemoResponses memos;

    public AttributeMemoSummaryResponse(String attribute, MemoResponses memos) {
        this.totalPrice = memos.calculateAttributeSum(attribute);
        this.attribute = attribute;
        this.memos = memos;
    }
}
