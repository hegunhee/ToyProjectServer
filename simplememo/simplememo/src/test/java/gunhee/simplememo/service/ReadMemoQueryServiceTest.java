package gunhee.simplememo.service;

import gunhee.simplememo.domain.memo.IncomeExpenseType;
import gunhee.simplememo.domain.memo.Memo;
import gunhee.simplememo.dto.memo.StaticsMemoDto;
import gunhee.simplememo.dto.memo.StaticsMemosResponse;
import gunhee.simplememo.repository.MemoRepository;
import gunhee.simplememo.service.memo.MemoReadService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReadMemoQueryServiceTest {

    @InjectMocks
    private MemoReadService memoReadService;

    @Mock
    private MemoRepository memoRepository;

    private final LocalDateTime dateNow = LocalDateTime.now();

    @Test
    public void findMemosByAttributeTest() {
        //given
        String attribute = "식비";
        List<Memo> memos = getSampleMemos();

        //when
        when(memoRepository.findMemosByAttribute(attribute,dateNow.getYear(), dateNow.getMonthValue()))
                .thenReturn(memos.stream()
                        .filter((memo) -> memo.getAttribute().equals(attribute)).toList());

        List<Memo> memosResult = memoReadService.findMemosByAttribute(attribute, dateNow.getYear(),dateNow.getMonthValue());
        BigDecimal sum = memosResult.stream()
                .map(Memo::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //then
        Assertions.assertAll(
                () -> assertThat(memosResult.size()).isEqualTo(2),
                () -> assertThat(sum.intValue()).isEqualTo(13000)
        );
    }

    @Test
    public void findMemosByIncomeExpenseTypeTest() {
        //given
        List<Memo> memos = getSampleMemos();

        //when
        when(memoRepository.sumMemoPricesByIncomeExpenseType(IncomeExpenseType.EXPENSE,dateNow.getYear(),dateNow.getMonthValue()))
                .thenReturn(memos.stream()
                        .filter((memo) -> memo.getIncomeExpenseType().equals(IncomeExpenseType.EXPENSE))
                        .map(Memo::getPrice)
                        .reduce(BigDecimal.ZERO,BigDecimal::add));

        BigDecimal totalSum = memoRepository.sumMemoPricesByIncomeExpenseType(IncomeExpenseType.EXPENSE, dateNow.getYear(), dateNow.getMonthValue());
        when(memoRepository.findMemosByIncomeExpenseType(totalSum,IncomeExpenseType.EXPENSE,dateNow.getYear(),dateNow.getMonthValue()))
                .thenReturn(getSampleStaticsMemos());

        //then
        StaticsMemosResponse staticsResponse = memoReadService.findStaticsMemo(IncomeExpenseType.EXPENSE, dateNow.getYear(), dateNow.getMonthValue());

        Assertions.assertAll(
                () -> assertThat(staticsResponse.getStaticsMemos().size()).isEqualTo(1),
                () -> assertThat(staticsResponse.getType()).isEqualTo(IncomeExpenseType.EXPENSE),
                () -> assertThat(staticsResponse.getTotalPrice()).isEqualTo(BigDecimal.valueOf(13000))
        );
    }

    private List<Memo> getSampleMemos() {
        Memo memo1 = new Memo(dateNow, IncomeExpenseType.EXPENSE, "식비", "현금", "맛있는 치킨", BigDecimal.valueOf(10000));
        Memo memo2 = new Memo(dateNow, IncomeExpenseType.INCOME, "용돈", "현금", "용돈", BigDecimal.valueOf(1500));
        Memo memo3 = new Memo(dateNow, IncomeExpenseType.EXPENSE, "식비", "현금", "맛있는 라면", BigDecimal.valueOf(3000));

        return List.of(memo1, memo2, memo3);
    }

    private List<StaticsMemoDto> getSampleStaticsMemos() {
        StaticsMemoDto staticsMemo = new StaticsMemoDto(BigDecimal.valueOf(100), "식비", BigDecimal.valueOf(13000));
        return List.of(staticsMemo);
    }
}
