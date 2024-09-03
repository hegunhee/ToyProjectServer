package gunhee.simplememo.service.memo;

import gunhee.simplememo.domain.memo.Memo;
import gunhee.simplememo.repository.MemoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Transactional
@Service
public class MemoService {

    private final MemoRepository memoRepository;

    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
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
