package gunhee.simplememo.dto.category;

import lombok.Getter;

@Getter
public class CategoryNameResponse {

    private final String name;

    public CategoryNameResponse(String name) {
        this.name = name;
    }
}
