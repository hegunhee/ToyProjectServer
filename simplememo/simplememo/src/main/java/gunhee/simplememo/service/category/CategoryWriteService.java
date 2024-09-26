package gunhee.simplememo.service.category;

import gunhee.simplememo.domain.category.Category;
import gunhee.simplememo.domain.category.CategoryType;
import gunhee.simplememo.repository.CategoryRepository;
import io.micrometer.core.annotation.Counted;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Counted("my.memo.write")
@Service
public class CategoryWriteService {

    private final CategoryRepository categoryRepository;

    public CategoryWriteService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

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

    public String delete(String categoryName) {
        categoryRepository.deleteById(categoryName);
        return categoryName;
    }
}
