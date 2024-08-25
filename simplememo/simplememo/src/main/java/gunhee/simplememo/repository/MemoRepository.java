package gunhee.simplememo.repository;

import gunhee.simplememo.domain.IncomeExpenseType;
import gunhee.simplememo.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoRepository extends JpaRepository<Memo,Integer> {

    @Query("select m from Memo m where YEAR(m.memoDate) = :year AND MONTH(m.memoDate) = :month ORDER BY DAY(m.memoDate) DESC")
    public List<Memo> findMemos(@Param("year") int year, @Param("month") int month);

    @Query("select m from Memo m where m.attribute = :attribute AND YEAR(m.memoDate) = :year AND MONTH(m.memoDate) = :month ORDER BY DAY(m.memoDate) DESC")
    public List<Memo> findMemosByAttribute(@Param("attribute") String Attribute,@Param("year") int year, @Param("month") int month);

    @Query("select m from Memo m where m.incomeExponseType = :type AND YEAR(m.memoDate) = :year AND MONTH(m.memoDate) = :month")
    public List<Memo> findMemosByIncomeExponseType(@Param("type")IncomeExpenseType type, @Param("year") int year, @Param("month") int month);
}
