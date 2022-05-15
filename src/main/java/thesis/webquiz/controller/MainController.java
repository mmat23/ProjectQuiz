package thesis.webquiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

import thesis.webquiz.model.QuizUser;
import thesis.webquiz.service.QuizUserService;

@Controller
public class MainController {

    @Autowired
    private QuizUserService userServ;

    @Autowired
    private HttpSession session;

    @GetMapping("/login")
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

    @GetMapping("/")
    public String mainPage(Model model) {
        return "redirect:/quiz/list";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("user", new QuizUser());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(QuizUser user, Model model, RedirectAttributes redirectAttrs) {
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            model.addAttribute("msg", "Пароль не был подтверждён");
            return "signup";
        }
        if (!userServ.save(user)) {
            model.addAttribute("msg", "Пользователь с таким email уже существует");
            return "signup";
        }
        redirectAttrs.addFlashAttribute("msg","Вы были успешно зарегистрированы");
        return "redirect:/login";
    }
}