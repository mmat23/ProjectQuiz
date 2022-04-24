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
import thesis.webquiz.model.Subject;
import thesis.webquiz.model.Answer;
import thesis.webquiz.service.QuizService;
import thesis.webquiz.service.SubjectService;

@Controller
public class QuizController {

    @Autowired
    private QuizService quizServ;

    @Autowired
    private SubjectService subjectServ;

    private Quiz btwQuiz;

    @GetMapping("/quiz")
    public String usersList(@RequestParam Long id, Model model) {
        Quiz quiz = quizServ.findById(id);
        quiz.setChoosedOptions(new ArrayList<Long>(quiz.getQuestions().size()));
        model.addAttribute("quiz", quiz);
        return "quiz";
    }

    @PostMapping("/quizResult")
    public String quizResult(Quiz quiz, Model model) {
        model.addAttribute("title", quiz.getTitle());
        model.addAttribute("ansRight", quizServ.getAndSaveResult(quiz));
        model.addAttribute("count", quiz.getChoosedOptions().size());
        return "quizResult";
    }

    @GetMapping("/quizCreate")
    public String quizCreation(Model model) {
        Quiz quiz = new Quiz();
        quiz.setSubjects(new ArrayList<Subject>(2));
        model.addAttribute("subjes", subjectServ.findAll());
        model.addAttribute("quiz", quiz);
        return "quizCreate";
    }

    @PostMapping("/quizCrtQuestions")
    public String quizCrtQuestion(Quiz quiz, Model model) {
        ArrayList<Question> questions = new ArrayList<Question>();
        for (int i = 0; i < quiz.getQuesCount(); i++) {
            Question ques = new Question();
            ArrayList<Answer> answers = new ArrayList<Answer>();
            for (int j = 0; j < 4; j++) {
                answers.add(new Answer());
            }
            ques.setAnswers(answers);
            System.out.print(ques.getAnswers().size());
            questions.add(ques);
        }
        quiz.setQuestions(questions);
        btwQuiz = quiz;
        model.addAttribute("quiz", quiz);
        return "quizCrtQuestions";
    }

    @PostMapping("/quizSave")
    public String saveQuizCont(Quiz quiz, Model model) {
        btwQuiz.setQuestions(quiz.getQuestions());
        quizServ.saveQuiz(btwQuiz);
        return "redirect:/login";
    }
}