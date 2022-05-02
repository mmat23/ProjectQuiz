package thesis.webquiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import thesis.webquiz.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    
}
