package thesis.webquiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import thesis.webquiz.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject findByName(String name);
}