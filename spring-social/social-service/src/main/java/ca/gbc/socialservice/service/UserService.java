package ca.gbc.socialservice.service;
import ca.gbc.socialservice.dto.CommentRequest;
import ca.gbc.socialservice.dto.UserRequest;
import ca.gbc.socialservice.dto.UserResponse;

import java.util.List;

public interface UserService {
    String createUser(UserRequest userRequest);

    void deleteUser(Long productId);

    void updateUser(Long userId, UserRequest userRequest);

    List<UserResponse> getAllUsers();
}
