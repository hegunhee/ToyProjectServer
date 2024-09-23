package gunhee.simplememo.domain.category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Category {

    private CategoryType type;

    @Id
    @Column(length = 10)
    private String name;

    protected Category() {

    }

    public Category(CategoryType type, String name) {
        this.type = type;
        this.name = name;
    }
}
