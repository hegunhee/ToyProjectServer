package gunhee.simplememo.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import gunhee.simplememo.domain.IncomeExpenseType;
import gunhee.simplememo.domain.Memo;
import gunhee.simplememo.dto.memo.MemoRequest;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.math.BigDecimal;

@Slf4j
public class MemoJsonTest {

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

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
        Assertions.assertThat(memo.getPrice()).isEqualTo(new BigDecimal("10000"));
        Assertions.assertThat(memo.getIncomeExpenseType()).isEqualTo(IncomeExpenseType.EXPENSE);
        Assertions.assertThat(memo.getMemoDate().getYear()).isEqualTo(2024);
    }
}
