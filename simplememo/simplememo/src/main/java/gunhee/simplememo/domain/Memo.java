package gunhee.simplememo.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
public class Memo {

    @Id @GeneratedValue
    @Column(name = "memo_id")
    private Integer id;

    private LocalDateTime memoDate;

    @Enumerated(value = EnumType.STRING)
    private IncomeExpenseType incomeExpenseType;

    private String attribute;

    private String asset;

    private String description;

    private BigDecimal price;

    protected Memo() {

    }

    public Memo(
            LocalDateTime memoDate,
            IncomeExpenseType incomeExpenseType,
            String attribute,
            String asset,
            String description,
            BigDecimal price) {
        this.memoDate = memoDate;
        this.incomeExpenseType = incomeExpenseType;
        this.attribute = attribute;
        this.asset = asset;
        this.description = description;
        this.price = price;
    }

    public void update(Memo updatedMemo) {
        memoDate = updatedMemo.getMemoDate();
        incomeExpenseType = updatedMemo.getIncomeExpenseType();
        attribute = updatedMemo.getAttribute();
        asset = updatedMemo.getAsset();
        description = updatedMemo.getDescription();
        price = updatedMemo.getPrice();
    }
}
