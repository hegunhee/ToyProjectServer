package gunhee.simplememo.service;

import gunhee.simplememo.domain.Memo;
import gunhee.simplememo.repository.MemoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<Memo> findMemosByDate(int year, int month) {
        return memoRepository.findMemosByDate(year,month);
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
