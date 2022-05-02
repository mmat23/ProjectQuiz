package thesis.webquiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import thesis.webquiz.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    
}
