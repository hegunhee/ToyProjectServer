package gunhee.simplememo.service;

import gunhee.simplememo.domain.memo.IncomeExpenseType;
import gunhee.simplememo.domain.memo.Memo;
import gunhee.simplememo.repository.MemoRepository;
import gunhee.simplememo.service.memo.ReadMemoService;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@ExtendWith(MockitoExtension.class)
public class ReadMemoServiceTest {

    @InjectMocks
    private ReadMemoService readMemoService;

    @Mock
    private MemoRepository memoRepository;

    @Test
    void findOne() {
        //given
        LocalDateTime date = LocalDateTime.now();
        String attribute = "식비";
        Memo memo = Mockito.spy(new Memo(date, IncomeExpenseType.EXPENSE, attribute, "우리은행", "샌드위치", new BigDecimal("10000")));
        //when
        doReturn(1).when(memo).getId();
        when(memoRepository.findById(memo.getId())).thenReturn(Optional.of(memo));

        Memo result = readMemoService.findById(memo.getId());

        //then
        Assertions.assertAll(
                () -> assertThat(result.getMemoDate()).isEqualTo(date),
                () -> assertThat(result.getAttribute()).isEqualTo(attribute)
        );
    }

    @Test
    void findNotPersistedData() {
        //given

        //when
        when(memoRepository.findById(100)).thenThrow(NoSuchElementException.class);

        //then
        Assertions.assertThrows(NoSuchElementException.class,() -> {
            readMemoService.findById(100);
        });
    }
}
