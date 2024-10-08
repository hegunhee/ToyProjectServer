package gunhee.simplememo.controller;

import gunhee.simplememo.domain.category.Category;
import gunhee.simplememo.domain.category.CategoryType;
import gunhee.simplememo.dto.category.CategoryNameResponse;
import gunhee.simplememo.dto.category.CategoryNamesResponse;
import gunhee.simplememo.dto.category.CategoryRequest;
import gunhee.simplememo.dto.category.CategoryResponse;
import gunhee.simplememo.service.category.CategoryWriteService;
import gunhee.simplememo.service.category.CategoryReadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category", description = "카테고리관련 API")
@RestController
public class CategoryController {

    private final CategoryWriteService categoryWriteService;
    private final CategoryReadService categoryReadService;

    public CategoryController(CategoryWriteService categoryWriteService, CategoryReadService categoryReadService) {
        this.categoryWriteService = categoryWriteService;
        this.categoryReadService = categoryReadService;
    }

    @Operation(summary = "카테고리 검색", description = "카테고리 이름을 바탕으로 카테고리 검색")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "저장되어있지 않은 카테고리입니다.")
    })
    @GetMapping("/v1/category/{categoryName}")
    public CategoryResponse findById(@PathVariable("categoryName") String name) {
        Category findCategory = categoryReadService.findById(name);
        return new CategoryResponse(findCategory.getType(),findCategory.getName());
    }

    @Operation(summary = "카테고리들 검색", description = "카테고리 타입을 기반으로 모든 카테고리 검색")
    @GetMapping("/v1/categories/categoryType/{categoryType}")
    public CategoryNamesResponse findAllByCategoryType(@PathVariable("categoryType") CategoryType type) {
        List<String> categoryNames = categoryReadService.findAllByCategoryType(type);
        return new CategoryNamesResponse(type,categoryNames);
    }

    @Operation(summary = "카테고리 유무 조회", description = "이름을 기반으로 해당 카테고리가 있는지 boolean값으로 반환")
    @GetMapping("/v1/category/existence/{categoryName}")
    public boolean existsBy(@PathVariable("categoryName") String name) {
        return categoryReadService.existsById(name);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "중복된 가계부입니다.")
    })
    @Operation(summary = "카테고리 저장", description = "카테고리 저장")
    @PostMapping("/v1/category")
    public CategoryNameResponse save(@RequestBody @Valid CategoryRequest categoryRequest) {
        String savedName = categoryWriteService.save(categoryRequest.getType(), categoryRequest.getName());
        return new CategoryNameResponse(savedName);
    }

    @Operation(summary = "카테고리 삭제", description = "카테고리 이름을 바탕으로 카테고리 삭제")
    @DeleteMapping("/v1/category/{categoryName}")
    public CategoryNameResponse delete(@PathVariable("categoryName") String name) {
        String deletedName = categoryWriteService.delete(name);
        return new CategoryNameResponse(deletedName);
    }
}
