package gunhee.simplememo.controller;

import gunhee.simplememo.domain.category.Category;
import gunhee.simplememo.domain.category.CategoryType;
import gunhee.simplememo.dto.category.CategoryRequest;
import gunhee.simplememo.dto.category.CategoryResponse;
import gunhee.simplememo.service.CategoryService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/v1/category/{categoryName}")
    public CategoryResponse findOne(@PathVariable("categoryName") String name) {
        Category findCategory = categoryService.findOne(name);
        return new CategoryResponse(findCategory.getType(),findCategory.getName());
    }

    @GetMapping("/v1/categories/categoryType/{categoryType}")
    public CategoryNamesResponse findAllBy(@PathVariable("categoryType") CategoryType type) {
        List<String> categoryNames = categoryService.findAllBy(type);
        return new CategoryNamesResponse(type,categoryNames);
    }

    @GetMapping("/v1/category/existence/{categoryName}")
    public boolean existsBy(@PathVariable("categoryName") String name) {
        return categoryService.existsBy(name);
    }

    @PostMapping("/v1/category")
    public CategoryNameResponse save(@RequestBody @Valid CategoryRequest categoryRequest) {
        String savedName = categoryService.save(categoryRequest.getType(), categoryRequest.getName());
        return new CategoryNameResponse(savedName);
    }

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
