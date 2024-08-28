package gunhee.simplememo.dto.memo;

import gunhee.simplememo.domain.memo.IncomeExpenseType;
import gunhee.simplememo.dto.TotalSum;

import java.math.BigDecimal;
import java.util.List;

public class MemosResponse {

    private final List<MemoResponse> memos;

    public MemosResponse(List<MemoResponse> memos) {
        this.memos = memos;
    }

    public List<MemoResponse> getMemos() {
        return List.copyOf(memos);
    }

    public TotalSum calculateTotalSum() {
        BigDecimal incomeSum = calculateSum(IncomeExpenseType.INCOME);
        BigDecimal expenseSum = calculateSum(IncomeExpenseType.EXPENSE);
        return new TotalSum(incomeSum,expenseSum);
    }

    private BigDecimal calculateSum(IncomeExpenseType type) {
        return memos.stream()
                .filter((memo) -> memo.getIncomeExpenseType() == type)
                .map(MemoResponse::getPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    public BigDecimal calculateAttributeSum(String attribute) {
        return memos.stream()
                .filter((memo) -> memo.getAttribute().equals(attribute))
                .map(MemoResponse::getPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }
}
