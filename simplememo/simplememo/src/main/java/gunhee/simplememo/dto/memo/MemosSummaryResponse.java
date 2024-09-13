package gunhee.simplememo.dto.memo;

import gunhee.simplememo.domain.memo.MemosVO;
import gunhee.simplememo.dto.TotalSum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "날짜를 기반으로 필터링된 DTO")
@Getter
public class MemosSummaryResponse {

    private final TotalSum totalSum;
    private final MemosResponse memos;

    public MemosSummaryResponse(MemosVO memosVO) {
        this.totalSum = memosVO.calculateTotalSumByMonth();
        this.memos = memosVO.toMemosResponses();
    }
}
