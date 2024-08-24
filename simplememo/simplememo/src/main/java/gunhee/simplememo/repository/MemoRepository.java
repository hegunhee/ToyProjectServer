package gunhee.simplememo.repository;

import gunhee.simplememo.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoRepository extends JpaRepository<Memo,Integer> {

    @Query("select m from Memo m where YEAR(m.memoDate) = :year AND MONTH(m.memoDate) = :month")
    public List<Memo> findMemosByDate(@Param("year") int year, @Param("month") int month);
}
