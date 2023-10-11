package ca.gbc.socialservice.repository;

import ca.gbc.socialservice.entities.Comment;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment,Long> {
    @DeleteQuery
    void deleteById(Long commentId);
}

