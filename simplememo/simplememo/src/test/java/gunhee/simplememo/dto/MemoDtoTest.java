package gunhee.simplememo.dto;

import gunhee.simplememo.domain.memo.IncomeExpenseType;
import gunhee.simplememo.domain.memo.Memo;
import gunhee.simplememo.domain.memo.MemosVO;
import gunhee.simplememo.dto.memo.AttributeMemoSummaryResponse;
import gunhee.simplememo.dto.memo.MemosResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class MemoDtoTest {

    private final LocalDateTime dateNow = LocalDateTime.now();

    @Test
    public void memoListToAttributeMemoSummaryResponse() {
        //when
        String attribute = "식비";
        List<Memo> memos = getSampleMemos().stream()
                .filter(memo -> memo.getAttribute().equals("식비"))
                .toList();

        MemosVO memosVO = new MemosVO(memos);

        MemosResponse memosResponse = memosVO.toMemosResponses();
        BigDecimal totalPrice = memosVO.calculateAttributeSum(attribute);

        AttributeMemoSummaryResponse memoSummaryResponse = new AttributeMemoSummaryResponse(totalPrice, attribute, memosResponse);
        //then
        Assertions.assertAll(
                () -> assertThat(memoSummaryResponse.getTotalPrice()).isEqualTo(totalPrice),
                () -> assertThat(memoSummaryResponse.getAttribute()).isEqualTo("식비"),
                () -> assertThat(memoSummaryResponse.getMemos().getMemos().size()).isEqualTo(memos.size())
        );
    }

    private List<Memo> getSampleMemos() {
        Memo memo1 = new Memo(dateNow, IncomeExpenseType.EXPENSE, "식비", "현금", "맛있는 치킨", BigDecimal.valueOf(10000));
        Memo memo2 = new Memo(dateNow, IncomeExpenseType.INCOME, "용돈", "현금", "용돈", BigDecimal.valueOf(1500));
        Memo memo3 = new Memo(dateNow, IncomeExpenseType.EXPENSE, "식비", "현금", "맛있는 라면", BigDecimal.valueOf(3000));

        return List.of(memo1, memo2, memo3);
    }
}
