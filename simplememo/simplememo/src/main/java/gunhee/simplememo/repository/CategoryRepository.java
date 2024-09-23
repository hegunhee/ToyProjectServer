package gunhee.simplememo.repository;

import gunhee.simplememo.domain.category.Category;
import gunhee.simplememo.domain.category.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,String> {

    @Query("select c.name from Category c where c.type = :type")
    public List<String> findAllByCategoryType(@Param("type") CategoryType type);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Category c WHERE c.name = :id")
    public boolean existsByName(@Param("id") String categoryName);
}
