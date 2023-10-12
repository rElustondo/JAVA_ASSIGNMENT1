package ca.gbc.socialservice.repository;

import ca.gbc.socialservice.entities.Comment;
import ca.gbc.socialservice.entities.UserEnt;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CommentRepository extends CrudRepository<Comment,Long> {
    @DeleteQuery
    void deleteById(Long commentId);
    Optional<Comment> findByContent(String content);
}

