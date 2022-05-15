package thesis.webquiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import thesis.webquiz.model.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    List<Quiz> findByTitle(String title);

    List<Quiz> findByDescription(String title);

}
