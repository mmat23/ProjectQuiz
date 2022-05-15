package thesis.webquiz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thesis.webquiz.model.Subject;
import thesis.webquiz.repository.SubjectRepository;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectRepository subjectRepos;

    @Override
    public List<Subject> findAll() {
        return subjectRepos.findAll();
    }
}
