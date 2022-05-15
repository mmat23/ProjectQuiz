package thesis.webquiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import thesis.webquiz.model.QuizUser;

public interface QuizUserRepository extends JpaRepository<QuizUser, Long> {
    QuizUser findByEmail(String email);

    List<QuizUser> findByUsername(String username);

    List<QuizUser> findByPassword(String password);

    
    List<QuizUser> findByRole(String role);

    @Transactional
    @Modifying
    @Query("update QuizUser u set u.ban=?1 where u.id=?2")
    void setBanById(Boolean ban, Long id);
}
