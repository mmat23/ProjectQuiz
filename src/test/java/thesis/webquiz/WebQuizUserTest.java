package thesis.webquiz;

import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.junit4.*;

import thesis.webquiz.model.*;
import thesis.webquiz.repository.*;

import org.springframework.test.context.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
class WebQuizUserTest {
    @Autowired
    private QuizUserRepository userRepo;

    @Autowired
    private QuizRepository quizRepo;

    @Autowired
    private CommentRepository comRepo;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@Test
    public void shouldSaveQuizUser() {
        QuizUser user = new QuizUser();
        user.setEmail("test@test");
        QuizUser saved = userRepo.save(user);
        assertThat(saved).usingRecursiveComparison().ignoringFields("userId").isEqualTo(user);
    }

    @Test
    public void shouldDeleteQuizUser() {
        QuizUser user = new QuizUser();
        user.setEmail("test@test");
        QuizUser saved = userRepo.save(user);
        userRepo.delete(saved);
        assertEquals(null, userRepo.findByEmail("test@test"));
    }

    @Test
    public void isNameUniqueQuizUser() {
        QuizUser user = new QuizUser();
        user.setEmail("test@test");
        userRepo.save(user);
        final QuizUser otherUser= new QuizUser();
        otherUser.setEmail("test@test");
        assertThrows(Exception.class, () -> userRepo.save(otherUser));
    }

    @Test
    public void canResetUniqueUser() {
        QuizUser user = new QuizUser();
        user.setEmail("test@test");
        user = userRepo.save(user);
        user.setEmail("test2@test");
        userRepo.save(user);
        assertEquals(null, userRepo.findByEmail("test@test"));
    }

    @Test
    public void canFindAllQuizUser() {
        QuizUser user = new QuizUser();
        user.setEmail("test1@test");
        userRepo.save(user);
        user = new QuizUser();
        user.setEmail("test2@test");
        userRepo.save(user);
        user = new QuizUser();
        user.setEmail("test3@test");
        userRepo.save(user);
        assertEquals(3, userRepo.findAll().size());
    }

    @Test
    public void canFindQuizUserByPassword() {
        QuizUser user = new QuizUser();
        user.setPassword("testpass");
        userRepo.save(user);
        user = new QuizUser();
        user.setPassword("testpass1");
        userRepo.save(user);
        user = new QuizUser();
        user.setPassword("testpass1");
        userRepo.save(user);
        assertEquals(2, userRepo.findByPassword("testpass1").size());
    }

    @Test
    public void canFindQuizUserByRoleAdmin() {
        QuizUser user = new QuizUser();
        user.setRole("USER");
        userRepo.save(user);
        user = new QuizUser();
        user.setRole("ADMIN");
        userRepo.save(user);
        user = new QuizUser();
        user.setRole("USER");
        userRepo.save(user);
        assertEquals(1, userRepo.findByRole("ADMIN").size());
    }

    @Test
    public void canFindQuizUserByRoleUser() {
        QuizUser user = new QuizUser();
        user.setRole("USER");
        userRepo.save(user);
        user = new QuizUser();
        user.setRole("ADMIN");
        userRepo.save(user);
        user = new QuizUser();
        user.setRole("USER");
        userRepo.save(user);
        assertEquals(2, userRepo.findByRole("USER").size());
    }

    @Test
    public void canDisableQuizUser() {
        QuizUser user = new QuizUser();
        user.setEmail("test@test");
        user = userRepo.save(user);
        user.setBan(false);
        user = userRepo.save(user);
        assertEquals(false, user.getBan());
    }

    @Test
    public void isQuizUserPasswordCrypted() {
        QuizUser QuizUser = new QuizUser();
        String pass = "test1";
        QuizUser.setPassword(bCryptPasswordEncoder.encode(pass));
        QuizUser = userRepo.save(QuizUser);
        assertTrue(pass != QuizUser.getPassword() && bCryptPasswordEncoder.matches(pass, QuizUser.getPassword()));
    }

    @Test
    public void canFindAllQuizzesFromUser() {
        QuizUser user = new QuizUser();
        user.getQuizzes().add(new Quiz());
        user.getQuizzes().add(new Quiz());
        user.getQuizzes().add(new Quiz());
        userRepo.save(user);
        assertEquals(3, quizRepo.findAll().size());
    }

    @Test
    public void canFindAllCommentsFromUser() {
        QuizUser user = new QuizUser();
        user.getComments().add(new Comment());
        user.getComments().add(new Comment());
        userRepo.save(user);
        assertEquals(2, comRepo.findAll().size());
    }
}