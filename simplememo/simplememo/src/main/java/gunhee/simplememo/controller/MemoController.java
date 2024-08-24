package gunhee.simplememo.controller;

import gunhee.simplememo.domain.Memo;
import gunhee.simplememo.dto.memo.MemoRequest;
import gunhee.simplememo.dto.memo.MemoResponse;
import gunhee.simplememo.service.MemoService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MemoController {

    private final MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @GetMapping("/v1/memo/{memoId}")
    public MemoResponse findMemo(@PathVariable("memoId") Integer memoId) {
        Memo findMemo = memoService.findOne(memoId);
        return new MemoResponse(findMemo);
    }

    @GetMapping("/v1/memos")
    public List<MemoResponse> findMemos(
            @RequestParam("year") int year,
            @RequestParam("month") int month,
            @RequestParam(value = "attribute",required = false) String attribute
    ) {
        List<Memo> memos = memoService.findMemosByDate(year, month);
        return memos.stream()
                .map(MemoResponse::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/v1/memo")
    public MemoIdResponse save(@RequestBody @Valid MemoRequest memoRequest) {
        Memo memo = memoRequest.toEntity();
        Integer memoId = memoService.save(memo);
        return new MemoIdResponse(memoId);
    }

    @PatchMapping("/v1/memo/{memoId}")
    public MemoIdResponse update(@PathVariable("memoId") Integer memoId, @RequestBody @Valid MemoRequest memoRequest) {
        memoService.update(memoId,memoRequest.toEntity());
        return new MemoIdResponse(memoId);
    }

    @DeleteMapping("vl/memo/{memoId}")
    public MemoIdResponse delete(@PathVariable("memoId") Integer memoId) {
        memoService.deleteById(memoId);
        return new MemoIdResponse(memoId);
    }

    @Getter
    static class MemoIdResponse {

        private final Integer memoId;

        public MemoIdResponse(Integer memoId) {
            this.memoId = memoId;
        }
    }
}
