package gunhee.simplememo.service;

import gunhee.simplememo.domain.category.Category;
import gunhee.simplememo.domain.category.CategoryType;
import gunhee.simplememo.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findOne(String categoryName) {
        Category category = categoryRepository.findById(categoryName).orElseThrow(() -> {
            throw new NoSuchElementException(categoryName+" 카테고리가 존재하지 않습니다.");
        });
        return category;
    }

    public List<String> findAllBy(CategoryType type) {
        return categoryRepository.findAllByCategoryType(type);
    }

    public boolean existsBy(String categoryName) {
        return categoryRepository.existsByName(categoryName);
    }

    @Transactional
    public String save(CategoryType type, String categoryName) {
        validateDuplicationCategory(categoryName);
        Category category = new Category(type,categoryName);
        categoryRepository.save(category);
        return category.getName();
    }

    private void validateDuplicationCategory(String categoryName) {
        if(categoryRepository.findById(categoryName).isPresent()) {
            throw new IllegalArgumentException("중복된 카테고리 입니다.");
        }
    }

    @Transactional
    public String delete(String categoryName) {
        categoryRepository.deleteById(categoryName);
        return categoryName;
    }
}
