package gunhee.simplememo.dto.category;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gunhee.simplememo.domain.category.Category;
import gunhee.simplememo.domain.category.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CategoryRequest {

    private final CategoryType type;

    @NotBlank(message = "이름값은 반드시 있어야합니다.")
    @Size(max = 10,message = "이름은 10글자까지 가능합니다.")
    private final String name;

    @JsonCreator
    public CategoryRequest(@JsonProperty("type") CategoryType type,@JsonProperty("name") String name) {
        this.type = type;
        this.name = name;
    }

    public Category toEntity() {
        return new Category(this.type,this.name);
    }
}
