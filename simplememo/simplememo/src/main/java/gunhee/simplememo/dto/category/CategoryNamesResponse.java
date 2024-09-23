package gunhee.simplememo.dto.category;

import gunhee.simplememo.domain.category.CategoryType;
import lombok.Getter;

import java.util.List;

@Getter
public class CategoryNamesResponse {

    private final CategoryType type;
    private final List<String> names;

    public CategoryNamesResponse(CategoryType type, List<String> names) {
        this.type = type;
        this.names = names;
    }
}
