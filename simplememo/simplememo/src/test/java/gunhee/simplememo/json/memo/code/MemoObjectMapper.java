package gunhee.simplememo.json.memo.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class MemoObjectMapper {

    private MemoObjectMapper() {

    }

    public static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
}
