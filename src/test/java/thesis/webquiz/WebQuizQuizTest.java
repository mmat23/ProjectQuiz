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
class WebQuizTest {
    @Autowired
    private QuizUserRepository userRepo;

    @Autowired
    private QuizRepository quizRepo;

    @Autowired
    private QuestionRepository quesRepo;

    @Autowired
    private CommentRepository comRepo;

	@Test
    public void shouldSaveQuiz() {
        Quiz quiz = Quiz.builder().title("test").build();
        Quiz saved = quizRepo.save(quiz);
        assertThat(saved).usingRecursiveComparison().ignoringFields("quizId").isEqualTo(quiz);
    }

    @Test
    public void shouldDeleteQuiz() {
        Quiz quiz = Quiz.builder().title("test").build();
        Quiz saved = quizRepo.save(quiz);
        quizRepo.delete(saved);
        assertEquals(0, quizRepo.findByTitle("test").size());
    }

    @Test
    public void canFindAllQuiz() {
        Quiz quiz = Quiz.builder().title("test1").build();
        quizRepo.save(quiz);
        quiz = Quiz.builder().title("test2").build();
        quizRepo.save(quiz);
        quiz = Quiz.builder().title("test3").build();
        quizRepo.save(quiz);
        assertEquals(3, quizRepo.findAll().size());
    }

    @Test
    public void canFindQuizByTitle() {
        Quiz quiz = Quiz.builder().title("test").build();
        quizRepo.save(quiz);
        quiz = Quiz.builder().title("test1").build();
        quizRepo.save(quiz);
        quiz = Quiz.builder().title("test1").build();
        quizRepo.save(quiz);
        assertEquals(1, quizRepo.findByTitle("test").size());
    }

    @Test
    public void shouldDeleteWithUser() {
        QuizUser user = new QuizUser();
        Quiz quiz = Quiz.builder().user(user).build();
        user.getQuizzes().add(quiz);
        quiz = quizRepo.save(quiz);
        user = userRepo.save(user);
        long id = quiz.getId();
        userRepo.delete(user);
        assertEquals(false, quizRepo.findById(id).isPresent());
    }

    @Test
    public void canFindAllQuestionsFromQuiz() {
        Quiz quiz = new Quiz();
        quiz.getQuestions().add(new Question());
        quiz.getQuestions().add(new Question());
        quizRepo.save(quiz);
        assertEquals(2, quesRepo.findAll().size());
    }

    @Test
    public void canFindAllCommentsFromQuiz() {
        Quiz quiz = new Quiz();
        quiz.getComments().add(new Comment());
        quiz.getComments().add(new Comment());
        quizRepo.save(quiz);
        assertEquals(2, comRepo.findAll().size());
    }
}