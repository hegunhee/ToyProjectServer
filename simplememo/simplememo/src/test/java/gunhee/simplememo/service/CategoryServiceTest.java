package gunhee.simplememo.service;

import gunhee.simplememo.domain.category.Category;
import gunhee.simplememo.domain.category.CategoryType;
import gunhee.simplememo.repository.CategoryRepository;
import gunhee.simplememo.service.category.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    void save() {
        //given
        Category category = new Category(CategoryType.ATTR_INCOME, "용돈");

        //when
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        String result = categoryService.save(category.getType(), category.getName());
        //then
        assertThat(category.getName()).isEqualTo(result);
    }

    @Test
    void saveDuplicateIdCategory() {
        //given
        Category category = new Category(CategoryType.ATTR_INCOME, "용돈");

        //when
        // category값이 이미 존재하다.
        when(categoryRepository.findById(category.getName())).thenReturn(Optional.of(category));

        //then
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            categoryService.save(category.getType(), category.getName());
        });
    }
}