package thesis.webquiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import thesis.webquiz.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    List<Comment> findByText(String text);

}
