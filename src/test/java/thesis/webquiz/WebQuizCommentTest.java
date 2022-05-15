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
class WebQuizCommentTest {
    @Autowired
    private QuizUserRepository userRepo;

    @Autowired
    private QuizRepository quizRepo;

    @Autowired
    private CommentRepository comRepo;

	@Test
    public void shouldSaveComment() {
        Comment com = new Comment();
        Comment saved = comRepo.save(com);
        assertThat(saved).usingRecursiveComparison().ignoringFields("commentId").isEqualTo(com);
    }

    @Test
    public void shouldDeleteComment() {
        Comment com = new Comment();
        com.setText("test");
        Comment saved = comRepo.save(com);
        comRepo.delete(saved);
        assertEquals(0, comRepo.findByText("test").size());
    }

    @Test
    public void canFindAllComment() {
        Comment com = new Comment();
        com.setText("test");
        comRepo.save(com);
        com = new Comment();
        com.setText("test");
        comRepo.save(com);
        com = new Comment();
        com.setText("test");
        comRepo.save(com);
        assertEquals(3, comRepo.findAll().size());
    }

    @Test
    public void canFindCommentByText() {
        Comment com = new Comment();
        com.setText("test");
        comRepo.save(com);
        com = new Comment();
        com.setText("test1");
        comRepo.save(com);
        com = new Comment();
        com.setText("test");
        comRepo.save(com);
        assertEquals(2, comRepo.findByText("test").size());
    }

    @Test
    public void shouldDeleteWithUser() {
        QuizUser user = new QuizUser();
        Comment com = new Comment();
        com.setText("test");
        user.getComments().add(com);
        com = comRepo.save(com);
        user = userRepo.save(user);
        long id = com.getId();
        userRepo.delete(user);
        assertEquals(false, comRepo.findById(id).isPresent());
    }

    @Test
    public void shouldDeleteWithQuiz() {
        Quiz quiz = new Quiz();
        Comment com = new Comment();
        com.setText("test");
        quiz.getComments().add(com);
        com = comRepo.save(com);
        quiz = quizRepo.save(quiz);
        long id = com.getId();
        quizRepo.delete(quiz);
        assertEquals(false, comRepo.findById(id).isPresent());
    }
}