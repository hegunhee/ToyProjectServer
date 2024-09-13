package gunhee.simplememo.json.memo;

import com.fasterxml.jackson.databind.ObjectMapper;
import gunhee.simplememo.domain.memo.IncomeExpenseType;
import gunhee.simplememo.domain.memo.Memo;
import gunhee.simplememo.dto.memo.MemoRequest;
import gunhee.simplememo.json.memo.code.MemoObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class MemoJsonTest {

    private final ObjectMapper mapper = MemoObjectMapper.mapper;

    @Test
    void JsonToMemoDtoTest() throws IOException {
        //given
        ClassPathResource resource = new ClassPathResource("memo/singlememo.json");
        MemoRequest memoDto = mapper.readValue(resource.getInputStream(), MemoRequest.class);
        //when
        Memo memo = memoDto.toEntity();
        String memoString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(memo);
        log.info("memo={}",memoString);
        //then
        Assertions.assertAll(
                () -> assertThat(memo.getPrice()).isEqualTo(new BigDecimal("10000")),
                () -> assertThat(memo.getIncomeExpenseType()).isEqualTo(IncomeExpenseType.EXPENSE),
                () -> assertThat(memo.getMemoDate().getYear()).isEqualTo(2024)
        );
    }
}
