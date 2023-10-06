package ca.gbc.socialservice.repository;

import ca.gbc.socialservice.entities.Post;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface PostRepository extends MongoRepository<Post, String> {
    @DeleteQuery
    void deleteById(UUID productId);
}
