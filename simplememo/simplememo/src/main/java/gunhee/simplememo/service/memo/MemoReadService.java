package gunhee.simplememo.service.memo;

import gunhee.simplememo.domain.memo.IncomeExpenseType;
import gunhee.simplememo.domain.memo.Memo;
import gunhee.simplememo.dto.memo.StaticsMemoDto;
import gunhee.simplememo.dto.memo.StaticsMemosResponse;
import gunhee.simplememo.repository.MemoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class MemoReadService {

    private final MemoRepository memoRepository;

    public MemoReadService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    public Memo findById(Integer memoId) {
        Memo memo = memoRepository.findById(memoId).orElseThrow(() -> {
            throw new IllegalArgumentException("메모를 찾지 못했습니다.");
        });
        return memo;
    }

    public List<Memo> findMemos(int year, int month) {
        return memoRepository.findMemos(year, month);
    }

    public List<Memo> findMemosByAttribute(String attribute,int year, int month) {
        return memoRepository.findMemosByAttribute(attribute,year,month);
    }

    public StaticsMemosResponse findStaticsMemo(IncomeExpenseType type, int year, int month) {
        BigDecimal sums = memoRepository.sumMemoPricesByIncomeExpenseType(type, year, month);
        BigDecimal newSums = adjustPrecisionAndScale(sums);
        List<StaticsMemoDto> staticsMemos = memoRepository.findMemosByIncomeExpenseType(newSums, type, year, month);
        return new StaticsMemosResponse(type,year,month,sums,staticsMemos);
    }

    private BigDecimal adjustPrecisionAndScale(BigDecimal currentBigDecimal) {
        int currentPrecision = currentBigDecimal.precision();
        int newPrecision = currentPrecision + currentBigDecimal.scale();
        return currentBigDecimal.setScale(newPrecision);
    }
}
