package gunhee.simplememo.controller;

import gunhee.simplememo.domain.category.Category;
import gunhee.simplememo.domain.category.CategoryType;
import gunhee.simplememo.dto.category.CategoryRequest;
import gunhee.simplememo.dto.category.CategoryResponse;
import gunhee.simplememo.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category", description = "카테고리관련 API")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "카테고리 검색", description = "카테고리 이름을 바탕으로 카테고리 검색")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "저장되어있지 않은 카테고리입니다.")

    })
    @GetMapping("/v1/category/{categoryName}")
    public CategoryResponse findOne(@PathVariable("categoryName") String name) {
        Category findCategory = categoryService.findOne(name);
        return new CategoryResponse(findCategory.getType(),findCategory.getName());
    }

    @Operation(summary = "카테고리들 검색", description = "카테고리 타입을 기반으로 모든 카테고리 검색")
    @GetMapping("/v1/categories/categoryType/{categoryType}")
    public CategoryNamesResponse findAllBy(@PathVariable("categoryType") CategoryType type) {
        List<String> categoryNames = categoryService.findAllBy(type);
        return new CategoryNamesResponse(type,categoryNames);
    }

    @Operation(summary = "카테고리 유무 조회", description = "이름을 기반으로 해당 카테고리가 있는지 boolean값으로 반환")
    @GetMapping("/v1/category/existence/{categoryName}")
    public boolean existsBy(@PathVariable("categoryName") String name) {
        return categoryService.existsBy(name);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "중복된 가계부입니다.")

    })
    @Operation(summary = "카테고리 저장", description = "카테고리 저장")
    @PostMapping("/v1/category")
    public CategoryNameResponse save(@RequestBody @Valid CategoryRequest categoryRequest) {
        String savedName = categoryService.save(categoryRequest.getType(), categoryRequest.getName());
        return new CategoryNameResponse(savedName);
    }

    @Operation(summary = "카테고리 삭제", description = "카테고리 이름을 바탕으로 카테고리 삭제")
    @DeleteMapping("/v1/category/{categoryName}")
    public CategoryNameResponse delete(@PathVariable("categoryName") String name) {
        String deletedName = categoryService.delete(name);
        return new CategoryNameResponse(deletedName);
    }


    @Getter
    static class CategoryNamesResponse {

        private final CategoryType type;
        private final List<String> names;

        public CategoryNamesResponse(CategoryType type, List<String> names) {
            this.type = type;
            this.names = names;
        }
    }

    @Getter
    static class CategoryNameResponse {

        private final String name;

        public CategoryNameResponse(String name) {
            this.name = name;
        }
    }
}
