package ca.gbc.friendshipservice.repository;

import ca.gbc.friendshipservice.model.FriendRequest;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface FriendRequestRepository extends MongoRepository<FriendRequest, String> {
    @DeleteQuery
    void deleteById(UUID postId);
}
