package gunhee.simplememo.service;

import gunhee.simplememo.domain.IncomeExpenseType;
import gunhee.simplememo.domain.Memo;
import gunhee.simplememo.dto.memo.StaticsMemoDto;
import gunhee.simplememo.dto.memo.StaticsMemosResponse;
import gunhee.simplememo.repository.MemoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class MemoService {

    private final MemoRepository memoRepository;

    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    public Memo findOne(Integer memoId) {
        Memo memo = memoRepository.findById(memoId).orElseThrow(() -> {
            throw new NoSuchElementException("메모를 찾지 못했습니다.");
        });
        return memo;
    }

    public List<Memo> findMemos(int year, int month) {
        return memoRepository.findMemos(year, month);
    }

    public List<Memo> findMemosByAttribute(String attribute,int year, int month) {
        return memoRepository.findMemosByAttribute(attribute,year,month);
    }

    public StaticsMemosResponse sumMemosPriceByIncomeExpenseType(IncomeExpenseType type,int year,int month) {
        BigDecimal sums = memoRepository.sumMemoPricesByIncomeExpenseType(type, year, month);
        List<StaticsMemoDto> staticsMemos = memoRepository.findMemosByIncomeExpenseType(sums, type, year, month);
        return new StaticsMemosResponse(type,year,month,sums,staticsMemos);
    }

    @Transactional
    public Integer save(Memo memo) {
        Memo savedMemo = memoRepository.save(memo);
        return savedMemo.getId();
    }

    @Transactional
    public Integer update(Integer memoId, Memo updatedMemo) {
        Memo memo = memoRepository.findById(memoId).orElseThrow(() -> {
            throw new NoSuchElementException("업데이트하려는 메모가 존재하지않습니다.");
        });
        memo.update(updatedMemo);
        return memoId;
    }

    @Transactional
    public Integer deleteById(Integer memoId) {
        memoRepository.deleteById(memoId);
        return memoId;
    }
}
