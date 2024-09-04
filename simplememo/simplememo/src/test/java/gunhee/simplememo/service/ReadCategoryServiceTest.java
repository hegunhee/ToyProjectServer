package gunhee.simplememo.service;

import gunhee.simplememo.domain.category.Category;
import gunhee.simplememo.domain.category.CategoryType;
import gunhee.simplememo.repository.CategoryRepository;
import gunhee.simplememo.service.category.ReadCategoryService;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReadCategoryServiceTest {

    @InjectMocks
    private ReadCategoryService readCategoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    void findOne() {
        //given
        Category category = new Category(CategoryType.ATTR_INCOME, "용돈");

        //when
        when(categoryRepository.findById(category.getName())).thenReturn(Optional.of(category));

        Category result = readCategoryService.findById(category.getName());
        //then
        Assertions.assertAll(
                () -> assertThat(result.getName()).isEqualTo(category.getName()),
                () -> assertThat(result.getType()).isEqualTo(category.getType())
        );
    }

    @Test
    void findOneButNotExists() {
        //given
        Category category = new Category(CategoryType.ATTR_INCOME, "용돈");

        //when
        when(categoryRepository.findById(category.getName())).thenReturn(Optional.empty());

        //then
        Assertions.assertThrows(NoSuchElementException.class, () -> readCategoryService.findById(category.getName()));
    }

    @Test
    void findAllBy() {
        //given
        Category categoryIncome1 = new Category(CategoryType.ATTR_INCOME, "용돈");
        Category categoryIncome2 = new Category(CategoryType.ATTR_INCOME, "월급");
        Category categoryExpense1 = new Category(CategoryType.ATTR_EXPENSE, "식비");

        List<String> incomeCategoryNames = Stream.of(categoryIncome1, categoryIncome2, categoryExpense1)
                .filter(category -> category.getType().equals(CategoryType.ATTR_INCOME))
                .map(Category::getName).toList();

        //when
        when(categoryRepository.findAllByCategoryType(CategoryType.ATTR_INCOME)).thenReturn(incomeCategoryNames);

        List<String> result = readCategoryService.findAllByCategoryType(CategoryType.ATTR_INCOME);

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

        boolean resultExist = readCategoryService.existsById(category.getName());
        boolean resultNotExist = readCategoryService.existsById(unknownName);
        //then
        Assertions.assertAll(
                () -> assertThat(resultExist).isTrue(),
                () -> assertThat(resultNotExist).isFalse()
        );
        assertThat(resultExist).isTrue();
        assertThat(resultNotExist).isFalse();
    }


}
