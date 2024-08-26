package gunhee.simplememo.service;

import gunhee.simplememo.domain.category.Category;
import gunhee.simplememo.domain.category.CategoryType;
import gunhee.simplememo.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

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
    void findOne() {
        //given
        Category category = new Category(CategoryType.ATTR_INCOME, "용돈");

        //when
        when(categoryRepository.findById(category.getName())).thenReturn(Optional.of(category));

        Category result = categoryService.findOne(category.getName());
        //then
        assertThat(category.getName()).isEqualTo(result.getName());
        assertThat(category.getType()).isEqualTo(result.getType());

    }

    @Test
    void findOneButNotExists() {
        //given
        Category category = new Category(CategoryType.ATTR_INCOME, "용돈");

        //when
        when(categoryRepository.findById(category.getName())).thenReturn(Optional.empty());

        //then
        Assertions.assertThrows(NoSuchElementException.class,() -> categoryService.findOne(category.getName()));
    }

    @Test
    void findAllBy() {
        //given
        Category categoryIncome1 = new Category(CategoryType.ATTR_INCOME, "용돈");
        Category categoryIncome2 = new Category(CategoryType.ATTR_INCOME, "월급");
        Category categoryExpense1 = new Category(CategoryType.ATTR_EXPENSE, "식비");

        List<String> incomeCategoryNames = Stream.of(categoryIncome1, categoryIncome2).map(Category::getName).toList();

        //when
        when(categoryRepository.findAllByCategoryType(CategoryType.ATTR_INCOME)).thenReturn(incomeCategoryNames);

        List<String> result = categoryService.findAllBy(CategoryType.ATTR_INCOME);

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void existsBy() {
        //given
        Category category = new Category(CategoryType.ATTR_INCOME, "용돈");
        String unknownName = "없는값";
        //when
        when(categoryRepository.existsByName(category.getName())).thenReturn(true);
        when(categoryRepository.existsByName(unknownName)).thenReturn(false);

        boolean resultExist = categoryService.existsBy(category.getName());
        boolean resultNotExist = categoryService.existsBy(unknownName);
        //then
        assertThat(resultExist).isTrue();
        assertThat(resultNotExist).isFalse();
    }

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