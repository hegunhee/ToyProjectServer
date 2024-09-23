package gunhee.simplememo.dto.memo;

import java.util.List;

public class MemosResponse {

    private final List<MemoResponse> memos;

    public MemosResponse(List<MemoResponse> memos) {
        this.memos = memos;
    }

    public List<MemoResponse> getMemos() {
        return List.copyOf(memos);
    }

}
