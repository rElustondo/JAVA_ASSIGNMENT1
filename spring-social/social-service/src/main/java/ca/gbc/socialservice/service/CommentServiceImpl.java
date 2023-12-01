package ca.gbc.socialservice.service;

import ca.gbc.socialservice.dto.CommentRequest;
import ca.gbc.socialservice.dto.CommentResponse;
import ca.gbc.socialservice.dto.UserRequest;
import ca.gbc.socialservice.dto.UserResponse;
import ca.gbc.socialservice.entities.Comment;
import ca.gbc.socialservice.entities.UserEnt;
import ca.gbc.socialservice.repository.CommentRepository;
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
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final WebClient webClient;

    @Value("${user.service.uri}")
    private String userApiUri;

    @Override
    public void createComment(CommentRequest commentRequest) {
        List<UserResponse> userResponseList = webClient
                .get()
                .uri(userApiUri)
                .retrieve()
                .bodyToFlux(UserResponse.class)
                .collectList()
                .block();

        UserResponse matchingUser = userResponseList.stream()
                .filter(user -> user.getId().equals(commentRequest.getUserId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User with ID " + commentRequest.getUserId() + " not found"));

        Comment newUser = new Comment(commentRequest.getContent(),commentRequest.getTimestamp(),commentRequest.getUserId(),matchingUser.getUsername());
        commentRepository.save(newUser);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public void updateComment(Long commentId,CommentRequest commentRequest) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if(optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setContent(commentRequest.getContent());
            comment.setTimestamp(commentRequest.getTimestamp());
            commentRepository.save(comment);
        }
    }

    @Override
    public List<CommentResponse> getAllComments() {
        List<Comment> comments = (List<Comment>) commentRepository.findAll();
        log.info("commentsAAAA {} before mapping", comments.size());
        return comments.stream().map(this::mapToCommentResponse).toList();
    }
    private CommentResponse mapToCommentResponse(Comment comment){
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .timestamp(comment.getTimestamp())
                .userId(comment.getUserId())
                .username(comment.getUsername())
                .build();
    }
}