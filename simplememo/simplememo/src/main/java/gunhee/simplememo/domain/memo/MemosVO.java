package gunhee.simplememo.domain.memo;

import gunhee.simplememo.dto.TotalSum;
import gunhee.simplememo.dto.memo.MemoResponse;
import gunhee.simplememo.dto.memo.MemosResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class MemosVO {

    private final List<Memo> memos;

    public MemosVO(List<Memo> memos) {
        this.memos = memos;
    }

    public TotalSum calculateTotalSumByMonth() {
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

    public BigDecimal calculateSumByAttribute(String attribute) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemosVO memosVO = (MemosVO) o;
        return Objects.equals(memos, memosVO.memos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memos);
    }
}
