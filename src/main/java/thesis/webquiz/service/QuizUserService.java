
package thesis.webquiz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import thesis.webquiz.model.QuizUser;
import thesis.webquiz.repository.QuizUserRepository;

@Service
public class QuizUserService  {

    @Autowired
    private QuizUserRepository quizUserRepos;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean save(QuizUser user) {
        if (quizUserRepos.findByEmail(user.getEmail()) != null)
            return false;
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        quizUserRepos.save(user);
        return true;
    }

    public List<QuizUser> findAll() {
        return quizUserRepos.findAll();
    }

    public void setBanById(Boolean ban, Long id) {
        quizUserRepos.setBanById(ban, id);
    }
}
