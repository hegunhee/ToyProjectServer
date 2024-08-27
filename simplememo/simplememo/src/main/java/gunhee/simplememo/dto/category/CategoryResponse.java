package gunhee.simplememo.dto.category;

import gunhee.simplememo.domain.category.CategoryType;
import lombok.Getter;

@Getter
public class CategoryResponse {

    private final CategoryType type;
    private final String name;

    public CategoryResponse(CategoryType type, String name) {
        this.type = type;
        this.name = name;
    }
}
