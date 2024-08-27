package gunhee.simplememo.controller;

import gunhee.simplememo.domain.IncomeExpenseType;
import gunhee.simplememo.domain.Memo;
import gunhee.simplememo.dto.memo.*;
import gunhee.simplememo.service.MemoService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public MemosSummaryResponse findMemos(
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
        List<Memo> memosByDate = memoService.findMemos(year, month);

        List<MemoResponse> memoResponses = memosByDate.stream()
                .map(MemoResponse::new)
                .toList();

        return new MemosSummaryResponse(new MemoResponses(memoResponses));
    }

    @GetMapping("/v1/memos/attribute/{attribute}")
    public AttributeMemoSummaryResponse findMemosByAttribute(
            @PathVariable("attribute") String attribute,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
        List<Memo> memosByAttribute = memoService.findMemosByAttribute(attribute, year, month);

        List<MemoResponse> memoResponses = memosByAttribute.stream()
                .map(MemoResponse::new)
                .toList();

        return new AttributeMemoSummaryResponse(attribute,new MemoResponses(memoResponses));
    }

    @GetMapping("/v1/memos/type/{type}")
    public StaticsMemosResponse findMemosByIncomeExpenseType(
            @PathVariable("type") IncomeExpenseType type,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
        return memoService.findStaticsMemo(type,year,month);
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

    @DeleteMapping("v1/memo/{memoId}")
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
