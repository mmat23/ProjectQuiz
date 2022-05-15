package thesis.webquiz.service;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thesis.webquiz.model.*;
import thesis.webquiz.pattern.Iterator;
import thesis.webquiz.repository.*;

@Service
public class QuizServiceImpl implements QuizService {
    @Autowired
    private AnswerRepository answerRepos;

    @Autowired
    private QuizRepository quizRepos;

    @Autowired
    private StatisticsRepository statRepos;

    @Autowired
    private SubjectRepository subjRepos;

    @Autowired
    private CommentRepository comRepos;

    @Autowired
    private QuizUserService userServ;

    @Override
    public Quiz findById(Long id) {
        return quizRepos.findById(id).get();
    }

    @Override
    public List<Quiz> findAll() {
        return quizRepos.findAll();
    }

    @Override
    public Long getAndSaveResult(Quiz quiz) {
        Long result = (long) 0;
        for (Long x : quiz.getChoosedOptions()) {
            if (answerRepos.findById(x).get().getCorrect())
                result += 1;
        }
        Statistics stat = findById(quiz.getId()).getStatistics();
        Long n = (long) quiz.getChoosedOptions().size();
        Long count = stat.getCountPlayed();
        Double avg = (double) stat.getAvgResult();
        stat.setAvgResult((avg * count + (double) result / n) / (count + 1));
        stat.setCountPlayed(count + 1);
        statRepos.save(stat);
        return result;
    }

    @Override
    public Boolean save(Quiz quiz) {
        List<Subject> subjects = quiz.getSubjects();
        for (int i = 0; i < subjects.size(); i++) {
            subjects.set(i, subjRepos.findByName(subjects.get(i).getName()));
            subjects.get(i).getQuizzes().add(quiz);
        }
        quiz.setSubjects(subjects.stream().distinct().collect(Collectors.toList()));
        Iterator<Question> quesIt = Question.iterator(quiz.getQuestions());
        while (quesIt.hasNext()) {
            Question ques = quesIt.next();
            ques.setQuiz(quiz);
            for (Answer ans : ques.getAnswers()) {
                ans.setQuestion(ques);
            }
            ques.getAnswers().get(ques.getCorrectAnsIndex()).setCorrect(true);
        }
        Statistics stat = new Statistics();
        stat.setQuiz(quiz);
        quiz.setStatistics(stat);
        quiz.setDate(LocalDate.now());
        
        QuizUser user = userServ.getCurrentUser();
        quiz.setUser(user);
        user.getQuizzes().add(quiz);
        quizRepos.save(quiz);
        return true;
    }

    @Override
    public Boolean makeComment(Comment comment) {
        QuizUser user = userServ.getCurrentUser();
        comment.setUser(user);
        user.getComments().add(comment);
        Quiz quiz = findById(comment.getQuiz().getId());
        comment.setQuiz(quiz);
        quiz.getComments().add(comment);
        comment.setDate(LocalDate.now());
        comRepos.save(comment);
        return true;
    }

    @Override
    public Double getAvgRating(Quiz quiz) {
        Double avgRating = 0.0;
        List<Comment> comments = quiz.getComments();
        if (comments.size() != 0) {
            for (Comment x : comments)
                avgRating += x.getRating();
            avgRating /= comments.size();
        }
        return avgRating;
    }

    @Override
    public void delete(Long id) {
        Quiz quiz = findById(id);
        if (quiz == null || !quiz.getUser().equals(userServ.getCurrentUser()))
            return;
        quizRepos.delete(quiz);
    }
}