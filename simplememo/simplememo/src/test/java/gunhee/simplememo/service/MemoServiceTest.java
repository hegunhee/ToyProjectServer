package gunhee.simplememo.service;

import gunhee.simplememo.domain.IncomeExpenseType;
import gunhee.simplememo.domain.Memo;
import gunhee.simplememo.repository.MemoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemoServiceTest {

    @InjectMocks
    private MemoService memoService;

    @Mock
    private MemoRepository memoRepository;

    @Test
    void findOne() {
        //given
        LocalDateTime date = LocalDateTime.now();
        Memo memo = Mockito.spy(new Memo(date, IncomeExpenseType.EXPENSE, "식비", "우리은행", "샌드위치", new BigDecimal("10000")));
        //when
        doReturn(1).when(memo).getId();
        when(memoRepository.findById(memo.getId())).thenReturn(Optional.of(memo));

        Memo result = memoService.findOne(memo.getId());

        //then
        assertThat(date).isEqualTo(result.getMemoDate());
        assertThat("식비").isEqualTo(result.getAttribute());
    }

    @Test
    void findNonExistedMemo() {
        //given

        //when
        when(memoRepository.findById(100)).thenThrow(NoSuchElementException.class);

        //then
        Assertions.assertThrows(NoSuchElementException.class,() -> {
            memoService.findOne(100);
        });

    }

    @Test
    void save() {
        //given
        LocalDateTime date = LocalDateTime.now();
        Memo memo = Mockito.spy(new Memo(date, IncomeExpenseType.EXPENSE, "식비", "우리은행", "샌드위치", new BigDecimal("10000")));

        //when
        doReturn(1).when(memo).getId();
        when(memoRepository.save(memo)).thenReturn(memo);

        Integer result = memoService.save(memo);
        //then
        assertThat(memo.getId()).isEqualTo(result);
    }

    @Test
    void update() {
        //given
        LocalDateTime date = LocalDateTime.now();
        Memo memo = Mockito.spy(new Memo(date, IncomeExpenseType.EXPENSE, "식비", "우리은행", "샌드위치", new BigDecimal("10000")));
        Memo updateMemo = new Memo(date, IncomeExpenseType.INCOME, "식비", "현금", "샌드위치", new BigDecimal("10000"));
        //when
        doReturn(1).when(memo).getId();
        when(memoRepository.findById(memo.getId())).thenReturn(Optional.of(memo));

        Memo result = memoService.findOne(memo.getId());
        result.update(updateMemo);

        //then
        assertThat(memo.getId()).isEqualTo(result.getId());
        assertThat(updateMemo.getIncomeExpenseType()).isEqualTo(result.getIncomeExpenseType());
        assertThat(updateMemo.getAsset()).isEqualTo(result.getAsset());
    }
}