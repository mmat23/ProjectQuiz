package thesis.webquiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import thesis.webquiz.model.QuizUser;
import thesis.webquiz.service.QuizUserService;

@Controller
public class MainController {

    @Autowired
    private QuizUserService quizUserServ;

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
}