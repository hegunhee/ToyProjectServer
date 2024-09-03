package gunhee.simplememo.controller;

import gunhee.simplememo.domain.memo.IncomeExpenseType;
import gunhee.simplememo.domain.memo.Memo;
import gunhee.simplememo.domain.memo.MemosVO;
import gunhee.simplememo.dto.TotalSum;
import gunhee.simplememo.dto.memo.*;
import gunhee.simplememo.service.memo.MemoService;
import gunhee.simplememo.service.memo.ReadMemoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class MemoController {

    private final MemoService memoService;
    private final ReadMemoService readMemoService;

    public MemoController(MemoService memoService, ReadMemoService readMemoService) {
        this.memoService = memoService;
        this.readMemoService = readMemoService;
    }

    @Operation(summary = "가계부 조회", description = "메모 아이디를 기반으로 특정 가계부 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "저장되어있지 않은 가계부입니다.")

    })
    @GetMapping("/v1/memo/{memoId}")
    public MemoResponse findMemo(@PathVariable("memoId") Integer memoId) {
        Memo findMemo = readMemoService.findById(memoId);
        return new MemoResponse(findMemo);
    }

    @Operation(summary = "가계부 조회", description = "년도와 월을 기반으로 가계부 조회")
    @GetMapping("/v1/memos")
    public MemosSummaryResponse findMemos(
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
        List<Memo> memosByDate = readMemoService.findMemos(year, month);

        MemosVO memosVO = new MemosVO(memosByDate);

        TotalSum totalSum = memosVO.calculateTotalSum();
        MemosResponse memosResponse = memosVO.toMemosResponses();

        return new MemosSummaryResponse(totalSum,memosResponse);
    }

    @Operation(summary = "분류를 기반으로 가계부 조회", description = "분류를 기반으로 가계부 조회")
    @GetMapping("/v1/memos/attribute/{attribute}")
    public AttributeMemoSummaryResponse findMemosByAttribute(
            @PathVariable("attribute") String attribute,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
        List<Memo> memosByAttribute = readMemoService.findMemosByAttribute(attribute, year, month);

        MemosVO memosVO = new MemosVO(memosByAttribute);

        BigDecimal totalPrice = memosVO.calculateAttributeSum(attribute);
        MemosResponse memoResponses = memosVO.toMemosResponses();

        return new AttributeMemoSummaryResponse(totalPrice,attribute,memoResponses);
    }

    @Operation(summary = "수입/지출 타입 기반으로 가계부 조회", description = "수입/지출 타입 기반으로 가계부 조회")
    @GetMapping("/v1/memos/type/{type}")
    public StaticsMemosResponse findMemosByIncomeExpenseType(
            @PathVariable("type") IncomeExpenseType type,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
        return readMemoService.findStaticsMemo(type,year,month);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "중복된 가계부입니다.")

    })
    @PostMapping("/v1/memo")
    public MemoIdResponse save(@RequestBody @Valid MemoRequest memoRequest) {
        Memo memo = memoRequest.toEntity();
        Integer memoId = memoService.save(memo);
        return new MemoIdResponse(memoId);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "저장되어있지 않은 가계부입니다.")

    })
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
}
