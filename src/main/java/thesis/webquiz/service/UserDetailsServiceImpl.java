package thesis.webquiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import thesis.webquiz.model.QuizUser;
import thesis.webquiz.repository.QuizUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private QuizUserRepository quizUserRepos;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        QuizUser user = quizUserRepos.findByEmail(email);

        if (user == null)
            throw new UsernameNotFoundException("User not found");

        return User.builder().password(user.getPassword()).username(user.getEmail()).roles(user.getRole()).build();
    }
}
