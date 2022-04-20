package thesis.webquiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import thesis.webquiz.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    
}