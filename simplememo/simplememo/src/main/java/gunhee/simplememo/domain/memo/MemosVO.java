package gunhee.simplememo.domain.memo;

import gunhee.simplememo.dto.TotalSum;
import gunhee.simplememo.dto.memo.MemoResponse;
import gunhee.simplememo.dto.memo.MemosResponse;

import java.math.BigDecimal;
import java.util.List;

public class MemosVO {

    private final List<Memo> memos;

    public MemosVO(List<Memo> memos) {
        this.memos = memos;
    }

    public TotalSum calculateTotalSum() {
        BigDecimal incomeSum = calculateSum(IncomeExpenseType.INCOME);
        BigDecimal expenseSum = calculateSum(IncomeExpenseType.EXPENSE);
        return new TotalSum(incomeSum,expenseSum);
    }

    private BigDecimal calculateSum(IncomeExpenseType type) {
        return memos.stream()
                .filter((memo) -> memo.getIncomeExpenseType() == type)
                .map(Memo::getPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    public BigDecimal calculateAttributeSum(String attribute) {
        return memos.stream()
                .filter((memo) -> memo.getAttribute().equals(attribute))
                .map(Memo::getPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    public MemosResponse toMemosResponses() {
        List<MemoResponse> memoResponses = this.memos.stream()
                .map(MemoResponse::new)
                .toList();

        return new MemosResponse(memoResponses);
    }
}
