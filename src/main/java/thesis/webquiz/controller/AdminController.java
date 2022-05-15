package thesis.webquiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import thesis.webquiz.service.QuizUserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private QuizUserService quizUserServ;

    @GetMapping("/users")
    public String usersList(Model model) {
        model.addAttribute("userList", quizUserServ.findAll());
        return "users";
    }

    @GetMapping("/setban")
    public String usersList(@RequestParam Long id, @RequestParam Boolean ban) {
        quizUserServ.setBanById(!ban, id);
        return "redirect:/admin/users";
    }
}