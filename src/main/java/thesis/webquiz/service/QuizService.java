package thesis.webquiz.service;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import thesis.webquiz.model.*;
import thesis.webquiz.repository.*;

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

    @Autowired
    private CommentRepository comRepos;

    @Autowired
    private QuizUserService userServ;

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
        Double avg = (double) stat.getAvgResult();
        stat.setAvgResult((avg * count + (double) result / n) / (count + 1));
        stat.setCountPlayed(count + 1);
        statRepos.save(stat);
        return result;
    }

    public Boolean saveQuiz(Quiz quiz) {
        List<Subject> subjects = quiz.getSubjects();
        if (subjects.get(0).getName().equals(subjects.get(1).getName())) {
            subjects.remove(1);
        }
        for (int i = 0; i < subjects.size(); i++) {
            subjects.set(i, subjRepos.findByName(subjects.get(i).getName()));
            subjects.get(i).getQuizzes().add(quiz);
        }
        for (Question ques : quiz.getQuestions()) {
            ques.setQuiz(quiz);
            for (Answer ans : ques.getAnswers()) {
                ans.setQuestion(ques);
            }
            ques.getAnswers().get(ques.getCorrectAnsIndex()).setCorrect(true);
        }
        Statistics stat = new Statistics();
        stat.setQuiz(quiz);
        quiz.setStatistics(stat);
        QuizUser user = userServ.getCurrentUser();
        quiz.setUser(user);
        user.getQuizzes().add(quiz);
        quizRepos.save(quiz);
        return true;
    }

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
}