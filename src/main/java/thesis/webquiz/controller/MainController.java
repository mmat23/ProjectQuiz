package thesis.webquiz.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import thesis.webquiz.model.Quiz;
import thesis.webquiz.model.QuizUser;
import thesis.webquiz.service.QuizService;
import thesis.webquiz.service.QuizUserService;

@Controller
public class MainController {

    @Autowired
    private QuizUserService quizUserServ;

    @Autowired
    private QuizService quizServ;

    @GetMapping("/prototype")
    public String prototypePage(Model model){
        return "prototype";
    }

    @GetMapping({"/", "/login"})
    public String login(Model model, String error) {
        if (error != null)
            model.addAttribute("msg", "Введён неправильный пароль или логин");
        return "login";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute("user", new QuizUser());
        return "reg";
    }

    @PostMapping("/registration")
    public String registration(QuizUser user, Model model) {
        if (!user.getPassword().equals(user.getPasswordConfirm())){
            model.addAttribute("msg", "Пароль не был подтверждён.");
            return "reg";
        }
        if (!quizUserServ.save(user)){
            model.addAttribute("msg", "Пользователь с таким email уже существует");
            return "reg";
        }
        return "redirect:/login";
    }

    @GetMapping("/users")
    public String usersList(Model model) {
        model.addAttribute("userList", quizUserServ.findAll());
        return "users";
    }

    @GetMapping("/setban")
    public String usersList(@RequestParam Long id, @RequestParam Boolean ban) {
        quizUserServ.setBanById(!ban, id);
        return "users";
    }

    @GetMapping("/quiz")
    public String usersList(@RequestParam Long id, Model model) {
        Quiz quiz = quizServ.findById(id);
        quiz.setChoosedOptions(new ArrayList<Long>(quiz.getQuestions().size()));
        model.addAttribute("quiz", quiz);
        return "quiz";
    }

    @PostMapping("/quizresult")
    public String quizResult(Quiz quiz, Model model){
        Long result[] = quizServ.getResult(quiz);
        model.addAttribute("title",quizServ.findById(quiz.getId()).getTitle());
        model.addAttribute("ansRight",result[0]);
        model.addAttribute("ansWrong",result[1]);
        return "quizresult";
    }
}
