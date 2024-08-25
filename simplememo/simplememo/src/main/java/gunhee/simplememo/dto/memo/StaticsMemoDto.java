package gunhee.simplememo.dto.memo;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class StaticsMemoDto {
    private final int percent;
    private final String attribute;
    private final BigDecimal price;


    public StaticsMemoDto(BigDecimal percent, String attribute, BigDecimal price) {
        this.percent = percent.intValue();
        this.attribute = attribute;
        this.price = price;
    }
}
