package thesis.webquiz.service;

import java.util.List;

import thesis.webquiz.model.*;

public interface QuizService {
    Quiz findById(Long id);

    List<Quiz> findAll();

    Boolean save(Quiz quiz);

    Long getAndSaveResult(Quiz quiz);

    Boolean makeComment(Comment comment);

    Double getAvgRating(Quiz quiz);

    void delete(Long id);
}