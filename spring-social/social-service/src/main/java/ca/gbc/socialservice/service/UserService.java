package ca.gbc.socialservice.service;
import ca.gbc.socialservice.dto.UserRequest;
import ca.gbc.socialservice.dto.UserResponse;

import java.util.List;

public interface UserService {
    void createUser(UserRequest userRequest);

    void deleteUser(Long productId);

    List<UserResponse> getAllUsers();
}
