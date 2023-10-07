package ca.gbc.socialservice.repository;

import ca.gbc.socialservice.entities.UserEnt;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository  extends CrudRepository<UserEnt,Long> {
    @DeleteQuery
    void deleteById(Long userId);
}
