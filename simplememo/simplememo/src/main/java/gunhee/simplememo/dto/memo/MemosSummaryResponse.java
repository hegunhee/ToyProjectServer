package gunhee.simplememo.dto.memo;

import gunhee.simplememo.dto.TotalSum;
import lombok.Getter;

@Getter
public class MemosSummaryResponse {

    private final TotalSum totalSum;
    private final MemosResponse memos;

    public MemosSummaryResponse(MemosResponse memos) {
        this.totalSum = memos.calculateTotalSum();
        this.memos = memos;
    }
}
