package thesis.webquiz.service;

import java.util.List;

import thesis.webquiz.model.QuizUser;

public interface QuizUserService  {

    Boolean save(QuizUser user);

    List<QuizUser> findAll();

    void setBanById(Boolean ban, Long id);

    void setBanById(Long id);

    QuizUser findById(Long id);

    QuizUser getCurrentUser();
}
