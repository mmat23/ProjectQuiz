package thesis.webquiz.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import thesis.webquiz.model.Question;
import thesis.webquiz.model.Quiz;
import thesis.webquiz.model.Answer;
import thesis.webquiz.service.QuizService;
import thesis.webquiz.service.SubjectService;

@Controller
public class QuizController {

    @Autowired
    private QuizService quizServ;

    @Autowired
    private SubjectService subjectServ;

    @GetMapping("/quiz")
    public String usersList(@RequestParam Long id, Model model) {
        Quiz quiz = quizServ.findById(id);
        quiz.setChoosedOptions(new ArrayList<Long>());
        model.addAttribute("quiz", quiz);
        return "quiz";
    }

    @PostMapping("/quiz")
    public String quizResult(Quiz quiz, Model model) {
        model.addAttribute("title", quiz.getTitle());
        model.addAttribute("ansRight", quizServ.getAndSaveResult(quiz));
        model.addAttribute("count", quiz.getChoosedOptions().size());
        return "quizResult";
    }

    @GetMapping("/quizCreate")
    public String quizCreation(Model model) {
        model.addAttribute("subjes", subjectServ.findAll());
        model.addAttribute("quiz", new Quiz());
        return "quizCreate";
    }

    @PostMapping("/quizCreate")
    public String saveQuizCont(Quiz quiz, Model model) {
        quizServ.saveQuiz(quiz);
        return "redirect:/login";
    }   
}