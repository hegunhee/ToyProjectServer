package gunhee.simplememo.dto.memo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;

@Schema(description = "통계 가계부들 DTO")
@Getter
public class StaticsMemoDto {
    private final int percent;
    private final String attribute;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final BigDecimal price;


    public StaticsMemoDto(BigDecimal percent, String attribute, BigDecimal price) {
        this.percent = percent.intValue();
        this.attribute = attribute;
        this.price = price;
    }
}
