package gunhee.simplememo.dto.category;

import gunhee.simplememo.domain.category.CategoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "카테고리 응답 DTO")
@Getter
public class CategoryResponse {

    private final CategoryType type;
    private final String name;

    public CategoryResponse(CategoryType type, String name) {
        this.type = type;
        this.name = name;
    }
}
