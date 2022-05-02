package thesis.webquiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

import thesis.webquiz.model.QuizUser;
import thesis.webquiz.service.QuizUserService;

@Controller
public class MainController {

    @Autowired
    private QuizUserService userServ;

    @Autowired
    private HttpSession session;

    @GetMapping("/prototype")
    public String prototypePage(Model model) {
        return "prototype";
    }

    @GetMapping({ "/", "/login" })
    public String loginPage(Model model, String error) {
        if (error != null) {
            String msg = ((AuthenticationException)session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION")).getMessage();
            if ("Bad credentials".equals(msg))
                model.addAttribute("msg", "Введён неправильный пароль или логин");
            else if ("User is disabled".equals(msg))
                model.addAttribute("msg", "Извините, но этот аккаунт заблокирован. Обращайтесь к администрации");
        }
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("user", new QuizUser());
        return "reg";
    }

    @PostMapping("/signup")
    public String signup(QuizUser user, Model model) {
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            model.addAttribute("msg", "Пароль не был подтверждён");
            return "reg";
        }
        if (!userServ.save(user)) {
            model.addAttribute("msg", "Пользователь с таким email уже существует");
            return "reg";
        }
        return "redirect:/login";
    }

    @GetMapping("/niceAdmin")
    public String niceAdmin(Model model){
        QuizUser user = new QuizUser();
        user.setEmail("admin@admin.by");
        user.setUsername("admin");
        user.setPassword("admin");
        user.setRole("ADMIN");
        userServ.save(user);
        model.addAttribute("msg", "Хороший админчик был зареган");
        return "login";
    }

    @GetMapping("/test")
    public String blah(){
        System.out.println(userServ.getCurrentUser());
        return "login";
    }
}