package thesis.webquiz.controller;

import java.util.ArrayList;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import thesis.webquiz.model.Quiz;
import thesis.webquiz.service.QuizService;
import thesis.webquiz.service.QuizUserService;
import thesis.webquiz.service.SubjectService;
import thesis.webquiz.model.Comment;

@Controller
public class QuizController {

    @Autowired
    private QuizService quizServ;

    @Autowired
    private SubjectService subjectServ;

    @Autowired
    private QuizUserService userServ;

    @GetMapping("/playQuiz")
    public String playQuizPage(@RequestParam Long id, Model model) {
        Quiz quiz = quizServ.findById(id);
        quiz.setChoosedOptions(new ArrayList<Long>());
        model.addAttribute("quiz", quiz);
        return "playQuiz";
    }

    @PostMapping("/playQuiz")
    public String quizResult(Quiz quiz, Model model) {
        model.addAttribute("title", quiz.getTitle());
        model.addAttribute("ansRight", quizServ.getAndSaveResult(quiz));
        model.addAttribute("count", quiz.getChoosedOptions().size());
        return "quizResult";
    }

    @GetMapping("/createQuiz")
    public String createQuizPage(Model model) {
        model.addAttribute("subjes", subjectServ.findAll());
        model.addAttribute("quiz", new Quiz());
        return "createQuiz";
    }

    @PostMapping("/createQuiz")
    public String createQuiz(Quiz quiz, Model model) {
        quizServ.saveQuiz(quiz);
        return "redirect:/login";
    }

    @GetMapping("/quiz")
    public String quizPage(@RequestParam Long id, Model model) {
        Quiz quiz = quizServ.findById(id);
        Comment comment = new Comment();
        comment.setQuiz(new Quiz());
        if (!(quiz.getUser().equals(userServ.getCurrentUser()))
                && !quiz.getComments().stream().anyMatch(s -> s.getUser().equals(userServ.getCurrentUser()))) {
            model.addAttribute("comForm", comment);
        }
        model.addAttribute("quiz", quizServ.findById(id));
        Double avgRating = quizServ.getAvgRating(quiz);
        if (!avgRating.equals(0.0)){
            model.addAttribute("avgRating", quizServ.getAvgRating(quiz));
        }
        return "quiz";
    }

    @PostMapping("/makeComment")
    public String makeComment(Comment comment) {
        quizServ.makeComment(comment);
        return "redirect:/quiz";
    }
}