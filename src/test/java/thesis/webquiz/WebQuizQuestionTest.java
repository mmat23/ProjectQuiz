package thesis.webquiz;

import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.junit4.*;

import thesis.webquiz.model.*;
import thesis.webquiz.repository.*;

import org.springframework.test.context.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
class WebQuizQuestionTest {
    @Autowired
    private QuizUserRepository userRepo;

    @Autowired
    private QuizRepository quizRepo;

    @Autowired
    private QuestionRepository quesRepo;

    @Autowired
    private AnswerRepository ansRepo;

	@Test
    public void shouldSaveQuestion() {
        Question ques = new Question();
        Question saved = quesRepo.save(ques);
        assertThat(saved).usingRecursiveComparison().ignoringFields("questionId").isEqualTo(ques);
    }

    @Test
    public void shouldDeleteQuestion() {
        Question ques = new Question();
        ques.setText("test");
        Question saved = quesRepo.save(ques);
        quesRepo.delete(saved);
        assertEquals(0, quesRepo.findByText("test").size());
    }

    @Test
    public void canFindAllComment() {
        Question ques = new Question();
        ques.setText("test");
        quesRepo.save(ques);
        ques = new Question();
        ques.setText("test");
        quesRepo.save(ques);
        ques = new Question();
        ques.setText("test");
        quesRepo.save(ques);
        assertEquals(3, quesRepo.findAll().size());
    }

    @Test
    public void canFindQuestionByText() {
        Question ques = new Question();
        ques.setText("test");
        quesRepo.save(ques);
        ques = new Question();
        ques.setText("test1");
        quesRepo.save(ques);
        ques = new Question();
        ques.setText("test");
        quesRepo.save(ques);
        assertEquals(2, quesRepo.findByText("test").size());
    }

    @Test
    public void shouldDeleteWithQuiz() {
        Quiz quiz = new Quiz();
        Question ques = new Question();
        ques.setText("test");
        quiz.getQuestions().add(ques);
        ques = quesRepo.save(ques);
        quiz = quizRepo.save(quiz);
        long id = ques.getId();
        quizRepo.delete(quiz);
        assertEquals(false, quesRepo.findById(id).isPresent());
    }

    @Test
    public void canFindAllAnswersFromQuestion() {
        Question ques = new Question();
        ques.getAnswers().add(new Answer());
        ques.getAnswers().add(new Answer());
        ques.getAnswers().add(new Answer());
        quesRepo.save(ques);
        assertEquals(3, ansRepo.findAll().size());
    }
}