package ca.gbc.socialservice.service;

import ca.gbc.socialservice.dto.PostResponse;
import ca.gbc.socialservice.dto.UserRequest;
import ca.gbc.socialservice.dto.UserResponse;
import ca.gbc.socialservice.entities.UserEnt;
import ca.gbc.socialservice.model.Post;
import ca.gbc.socialservice.repository.PostRepository;
import ca.gbc.socialservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    @Override
    public void createUser(UserRequest userRequest) {
        UserEnt newUser = new UserEnt(userRequest.getUsername(),userRequest.getEmail());
        userRepository.save(newUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<UserEnt> users = (List<UserEnt>) userRepository.findAll();
        return users.stream().map(this::mapToUserResponse).toList();
    }
    private UserResponse mapToUserResponse(UserEnt user){
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
