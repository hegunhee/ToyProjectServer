package gunhee.simplememo.service.category;

import gunhee.simplememo.domain.category.Category;
import gunhee.simplememo.domain.category.CategoryType;
import gunhee.simplememo.repository.CategoryRepository;
import io.micrometer.core.annotation.Counted;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Counted("my.memo.read")
@Service
public class CategoryReadService {

    private final CategoryRepository categoryRepository;

    public CategoryReadService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findById(String categoryName) {
        Category category = categoryRepository.findById(categoryName).orElseThrow(() -> {
            throw new IllegalArgumentException(categoryName+" 카테고리가 존재하지 않습니다.");
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
