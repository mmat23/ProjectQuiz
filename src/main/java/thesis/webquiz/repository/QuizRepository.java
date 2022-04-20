package thesis.webquiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import thesis.webquiz.model.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    
}
