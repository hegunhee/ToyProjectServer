package gunhee.simplememo.json.memo;

import com.fasterxml.jackson.databind.ObjectMapper;
import gunhee.simplememo.dto.memo.MemoRequest;
import gunhee.simplememo.json.memo.code.MemoObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class validationTest {


    private final ObjectMapper mapper = MemoObjectMapper.mapper;
    private final Map<String,String> emptyValidateMap = new HashMap<>();
    private final Map<String,String> sizeValidateMap = new HashMap<>();

    private Validator validator;

    @BeforeEach
    public void setUp() {
        initEmptyValidateMap();
        initSizeValidateMap();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private void initEmptyValidateMap() {
        emptyValidateMap.put("asset","지불타입은 빈값이 될 수 없습니다.");
        emptyValidateMap.put("attribute","분류는 빈값이 될 수 없습니다.");
    }

    private void initSizeValidateMap() {
        sizeValidateMap.put("asset","10글자 이상이면 안됩니다.");
        sizeValidateMap.put("attribute","10글자 이상이면 안됩니다.");
        sizeValidateMap.put("description","50글자 이상이면 안됩니다.");
    }

    @AfterEach
    public void tearDown() {
        emptyValidateMap.clear();
        sizeValidateMap.clear();
    }

    @Test
    void noViolateValidate() throws IOException {
        //given
        MemoRequest memoRequest = getMemoRequest("memo/singlememo.json");

        //when
        Set<ConstraintViolation<MemoRequest>> violations = validator.validate(memoRequest);
        //then
        assertThat(violations.isEmpty()).isEqualTo(true);
    }

    @Test
    void assetPropertyEmpty() throws IOException {
        //given
        MemoRequest memoRequest = getMemoRequest("memo/validate/blank/assetBlankMemo.json");

        //when
        Set<ConstraintViolation<MemoRequest>> violations = validator.validate(memoRequest);
        //then
        assertThat(violations.isEmpty()).isEqualTo(false);
        validateProperties(emptyValidateMap,violations);
    }

    @Test
    void assetAndAttributePropertyBlank() throws IOException {
        //given
        MemoRequest memoRequest = getMemoRequest("memo/validate/blank/assetAndAttributeBlankMemo.json");

        //when
        Set<ConstraintViolation<MemoRequest>> violations = validator.validate(memoRequest);
        //then
        assertThat(violations.size()).isEqualTo(2);
        validateProperties(emptyValidateMap,violations);
    }

    @Test
    void assetViolateSize() throws IOException {
        //given
        MemoRequest memoRequest = getMemoRequest("memo/validate/size/assetSizeMoreTenMemo.json");

        //when
        Set<ConstraintViolation<MemoRequest>> violations = validator.validate(memoRequest);
        //then
        assertThat(violations.isEmpty()).isEqualTo(false);
        validateProperties(sizeValidateMap,violations);
    }

    @Test
    void assertBlankAndDescriptionViolateSize() throws IOException {
        //given
        MemoRequest memoRequest = getMemoRequest("memo/validate/assetBlankDescriptionSizeMemo.json");

        //when
        Set<ConstraintViolation<MemoRequest>> violations = validator.validate(memoRequest);
        //then
        assertThat(violations.size()).isEqualTo(2);
    }

    private void validateProperties(Map<String,String> validateMap, Set<ConstraintViolation<MemoRequest>> violations) {
        for (ConstraintViolation<MemoRequest> violation : violations) {
            String propertyName = violation.getPropertyPath().toString();
            if(validateMap.get(propertyName) != null) {
                log.info("propertyName={}",propertyName);
                log.info("message={}",violation.getMessage());
                assertThat(violation.getMessage()).isEqualTo(validateMap.get(propertyName));
            }
        }
    }

    private MemoRequest getMemoRequest(String classPath) throws IOException {
        ClassPathResource resource = new ClassPathResource(classPath);
        return mapper.readValue(resource.getInputStream(), MemoRequest.class);
    }
}
