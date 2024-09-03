package gunhee.simplememo.service.category;

import gunhee.simplememo.domain.category.Category;
import gunhee.simplememo.domain.category.CategoryType;
import gunhee.simplememo.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Transactional(readOnly = true)
@Service
public class ReadCategoryService {

    private final CategoryRepository categoryRepository;

    public ReadCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findById(String categoryName) {
        Category category = categoryRepository.findById(categoryName).orElseThrow(() -> {
            throw new NoSuchElementException(categoryName+" 카테고리가 존재하지 않습니다.");
        });
        return category;
    }

    public List<String> findAllByCategoryType(CategoryType type) {
        return categoryRepository.findAllByCategoryType(type);
    }

    public boolean existsById(String categoryName) {
        return categoryRepository.existsByName(categoryName);
    }

}
