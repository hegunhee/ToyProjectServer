package gunhee.simplememo.service.memo;

import gunhee.simplememo.domain.memo.Memo;
import gunhee.simplememo.repository.MemoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MemoWriteService {

    private final MemoRepository memoRepository;

    public MemoWriteService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    public Integer save(Memo memo) {
        Memo savedMemo = memoRepository.save(memo);
        return savedMemo.getId();
    }

    public Integer update(Integer memoId, Memo updatedMemo) {
        Memo persistedMemo = memoRepository.findById(memoId).orElseThrow(() -> {
            throw new IllegalArgumentException("업데이트하려는 메모가 존재하지않습니다.");
        });

        persistedMemo.updateDate(updatedMemo.getMemoDate());
        persistedMemo.updateIncomeExpenseType(updatedMemo.getIncomeExpenseType());
        persistedMemo.updateAttribute(updatedMemo.getAttribute());
        persistedMemo.updateAsset(updatedMemo.getAsset());
        persistedMemo.updatePrice(updatedMemo.getPrice());
        persistedMemo.updateDescription(updatedMemo.getDescription());

        return memoId;
    }

    public Integer deleteById(Integer memoId) {
        memoRepository.deleteById(memoId);
        return memoId;
    }
}
