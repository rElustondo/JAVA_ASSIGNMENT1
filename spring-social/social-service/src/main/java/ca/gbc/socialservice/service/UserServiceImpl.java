package ca.gbc.socialservice.service;

import ca.gbc.socialservice.dto.*;
import ca.gbc.socialservice.entities.Comment;
import ca.gbc.socialservice.entities.UserEnt;
import ca.gbc.socialservice.model.Post;
import ca.gbc.socialservice.repository.PostRepository;
import ca.gbc.socialservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final WebClient webClient;

    @Value("${friend.service.uri}")
    private String friendRequestApiUri;
    @Override
    public void createUser(UserRequest userRequest) {
        UserEnt newUser = new UserEnt(userRequest.getUsername(),userRequest.getEmail(), userRequest.getPassword());
        userRepository.save(newUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void updateUser(Long userId, UserRequest userRequest) {
        Optional<UserEnt> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()) {
            UserEnt user = optionalUser.get();
            user.setUsername(userRequest.getUsername());
            user.setEmail(userRequest.getEmail());
            user.setPassword(userRequest.getPassword());
            userRepository.save(user);
        }
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<UserEnt> users = (List<UserEnt>) userRepository.findAll();
        List<FriendRequestResponse> allFriendRequests = webClient
                .get()
                .uri(friendRequestApiUri)
                .retrieve()
                .bodyToFlux(FriendRequestResponse.class)
                .collectList()
                .block();


        return users.stream()
                .map(user -> mapToUserResponse(user, allFriendRequests))
                .toList();
    }
    private UserResponse mapToUserResponse(UserEnt user, List<FriendRequestResponse> allFriendRequests){
        List<FriendRequestResponse> requestsReceived = allFriendRequests.stream()
                .filter(request -> request.getReceiverId().equals(user.getId()))
                .toList();


        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .friendRequests(requestsReceived)
                .build();
    }
}
