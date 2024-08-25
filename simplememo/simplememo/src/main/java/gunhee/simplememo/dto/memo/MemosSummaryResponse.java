package gunhee.simplememo.dto.memo;

import gunhee.simplememo.dto.TotalSum;
import lombok.Getter;

@Getter
public class MemosSummaryResponse {

    private final TotalSum totalSum;
    private final MemoResponses memos;

    public MemosSummaryResponse(MemoResponses memos) {
        this.totalSum = memos.calculateTotalSum();
        this.memos = memos;
    }
}
