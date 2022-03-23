package tesis.webquiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import tesis.webquiz.model.QuizUser;

public interface QuizUserRepository extends JpaRepository<QuizUser, Long> {
    QuizUser findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update QuizUser u set u.ban=?1 where u.id=?2")
    void setBanById(Boolean ban, Long id);
}
