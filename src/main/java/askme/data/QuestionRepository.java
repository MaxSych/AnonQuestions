package askme.data;

import askme.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByUserId(Long userId);

    List<Question> findByUserIdAndIsAnsweredTrue(Long userId);
}
