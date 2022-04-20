package thesis.webquiz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thesis.webquiz.model.Quiz;
import thesis.webquiz.repository.AnswerRepository;
import thesis.webquiz.repository.QuizRepository;

@Service
public class QuizService {
    @Autowired
    private AnswerRepository answerRepos;

    @Autowired
    private QuizRepository quizRepos;

    public Quiz findById(Long id) {
        return quizRepos.findById(id).get();
    }

    public List<Quiz> findAll() {
        return quizRepos.findAll();
    }

    public Long[] getResult(Quiz quiz) {
        Long result[] = { (long) 0, (long) 0 };
        for (Long x : quiz.getChoosedOptions()) {
            if (answerRepos.findById(x).get().getIsTrue())
                result[0] += 1;
            else
                result[1] += 1;
        }
        return result;
    }
}
