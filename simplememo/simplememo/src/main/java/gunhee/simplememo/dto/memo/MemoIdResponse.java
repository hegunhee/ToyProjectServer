package gunhee.simplememo.dto.memo;

import lombok.Getter;

@Getter
public class MemoIdResponse {

    private final Integer memoId;

    public MemoIdResponse(Integer memoId) {
        this.memoId = memoId;
    }
}
