package thesis.webquiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import thesis.webquiz.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    
    List<Question> findByText(String text);

}
