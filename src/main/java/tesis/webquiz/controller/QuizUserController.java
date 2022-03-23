package tesis.webquiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tesis.webquiz.model.QuizUser;
import tesis.webquiz.service.QuizUserService;

@Controller
public class QuizUserController {

    @Autowired
    private QuizUserService quizUserServ;

    @GetMapping({"/", "/login"})
    public String login(Model model, String error) {
        if (error != null)
            model.addAttribute("error", "Введён неправильный пароль или логин");
        return "login";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute("userForm", new QuizUser());
        return "reg";
    }

    @PostMapping("/registration")
    public String registration(QuizUser userForm, Model model) {
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("msg", "Пароль не был подтверждён.");
            return "reg";
        }
        if (!quizUserServ.save(userForm)){
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
