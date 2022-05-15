package thesis.webquiz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import thesis.webquiz.model.QuizUser;
import thesis.webquiz.repository.QuizUserRepository;

@Service
public class QuizUserServiceImpl implements QuizUserService  {

    @Autowired
    private QuizUserRepository userRepos;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Boolean save(QuizUser user) {
        if (userRepos.findByEmail(user.getEmail()) != null)
            return false;
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepos.save(user);
        return true;
    }

    @Override
    public List<QuizUser> findAll() {
        return userRepos.findAll();
    }

    @Override
    public void setBanById(Boolean ban, Long id) {
        userRepos.setBanById(ban, id);
    }

    @Override
    public QuizUser findById(Long id) {
        return userRepos.findById(id).get();
    } 

    @Override
    public QuizUser getCurrentUser(){
        return userRepos.findByEmail(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
    }

    @Override
    public void setBanById(Long id) {
        QuizUser user = findById(id);
        userRepos.setBanById(!user.getBan(), id);
    }
}
