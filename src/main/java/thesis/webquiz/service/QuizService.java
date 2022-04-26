package thesis.webquiz.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thesis.webquiz.model.Answer;
import thesis.webquiz.model.Question;
import thesis.webquiz.model.Quiz;
import thesis.webquiz.model.Statistics;
import thesis.webquiz.model.Subject;
import thesis.webquiz.repository.AnswerRepository;
import thesis.webquiz.repository.StatisticsRepository;
import thesis.webquiz.repository.SubjectRepository;
import thesis.webquiz.repository.QuizRepository;

@Service
public class QuizService {
    @Autowired
    private AnswerRepository answerRepos;

    @Autowired
    private QuizRepository quizRepos;

    @Autowired
    private StatisticsRepository statRepos;

    @Autowired
    private SubjectRepository subjRepos;

    public Quiz findById(Long id) {
        return quizRepos.findById(id).get();
    }

    public List<Quiz> findAll() {
        return quizRepos.findAll();
    }

    public Long getAndSaveResult(Quiz quiz) {
        Long result = (long) 0;
        for (Long x : quiz.getChoosedOptions()) {
            if (answerRepos.findById(x).get().getCorrect())
                result += 1;
        }
        Statistics stat = findById(quiz.getId()).getStatistics();
        Long n = (long) quiz.getChoosedOptions().size();
        Long count = stat.getCountPlayed();
        Double avg = (double)stat.getAvgResult();
        stat.setAvgResult((avg * count + (double) result / n)/(count + 1));
        stat.setCountPlayed(count + 1);
        statRepos.save(stat);
        return result;
    }

    public Boolean saveQuiz(Quiz quiz){
        List<Subject> subjects = quiz.getSubjects();
        if (subjects.get(0).getName().equals(subjects.get(1).getName())){
            subjects.remove(1);
        }
        for (int i = 0; i < subjects.size(); i++){
            subjects.set(i, subjRepos.findByName(subjects.get(i).getName()));
            subjects.get(i).getQuizzes().add(quiz);
        }
        for (Question ques : quiz.getQuestions()){
            ques.setQuiz(quiz);
            for (Answer ans : ques.getAnswers()){
                ans.setQuestion(ques);
            }
            ques.getAnswers().get(ques.getCorrectAnsIndex()).setCorrect(true);
        }
        Statistics stat = new Statistics();
        stat.setQuiz(quiz);
        quiz.setStatistics(stat);
        statRepos.save(stat);
        return true;
    }
}