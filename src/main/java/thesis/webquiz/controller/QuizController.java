package thesis.webquiz.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import thesis.webquiz.model.*;
import thesis.webquiz.service.*;

@Controller
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizServ;

    @Autowired
    private SubjectService subjectServ;

    @Autowired
    private QuizUserService userServ;

    @GetMapping("/play")
    public String playQuizPage(@RequestParam Long id, Model model) {
        Quiz quiz = quizServ.findById(id);
        quiz.setChoosedOptions(new ArrayList<Long>());
        model.addAttribute("quiz", quiz);
        return "playQuiz";
    }

    @PostMapping("/play")
    public String quizResult(Quiz quiz, Model model) {
        model.addAttribute("title", quiz.getTitle());
        model.addAttribute("ansRight", quizServ.getAndSaveResult(quiz));
        model.addAttribute("count", quiz.getChoosedOptions().size());
        return "quizResult";
    }

    @GetMapping("/create")
    public String createQuizPage(Model model) {
        model.addAttribute("subjes", subjectServ.findAll());
        model.addAttribute("quiz", new Quiz());
        return "createQuiz";
    }

    @PostMapping("/create")
    public String createQuiz(Quiz quiz, Model model) {
        quizServ.save(quiz);
        return "redirect:/quiz/myQuizzes";
    }

    @GetMapping("/show")
    public String quizPage(@RequestParam Long id, Model model) {
        Quiz quiz = quizServ.findById(id);
        Comment comment = new Comment();
        comment.setQuiz(new Quiz());
        if (!quiz.getUser().equals(userServ.getCurrentUser())
                && !quiz.getComments().stream().anyMatch(s -> s.getUser().equals(userServ.getCurrentUser()))) {
            model.addAttribute("comForm", comment);
        }
        model.addAttribute("quiz", quizServ.findById(id));
        double avgRating = quizServ.getAvgRating(quiz);
        if (avgRating != 0.0){
            model.addAttribute("avgRating", avgRating);
        }
        return "quiz";
    }

    @PostMapping("/comment")
    public String makeComment(Comment comment, HttpServletRequest request) {
        quizServ.makeComment(comment);
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("/myQuizzes")
    public String myQuizzesPage(Model model) {
        QuizUser user = userServ.getCurrentUser();
        model.addAttribute("quizzes", user.getQuizzes());
        return "myQuizzes";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id, Model model) {
        quizServ.delete(id);
        return "redirect:/quiz/myQuizzes";
    }

    @GetMapping("/list")
    public String listPage(Model model) {
        model.addAttribute("quizzes", quizServ.findAll());
        return "quizzes";
    }
}