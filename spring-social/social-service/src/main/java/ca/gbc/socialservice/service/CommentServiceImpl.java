package ca.gbc.socialservice.service;

import ca.gbc.socialservice.dto.*;
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
    @Value("${post.service.uri}")
    private String postApiUri;

    @Override
    public void createComment(CommentRequest commentRequest) {
        // making sure there is a post with that id
        List<PostResponse> postResponseList = webClient
                .get()
                .uri(postApiUri)
                .retrieve()
                .bodyToFlux(PostResponse.class)
                .collectList()
                .block();
        PostResponse matchingPost = postResponseList.stream()
                .filter(post -> post.getId().equals(commentRequest.getPostId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Post with ID " + commentRequest.getPostId() + " not found"));
        // making sure there is a user with that id and adding the details of username to the comment
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

        Comment newUser = new Comment(commentRequest.getContent(),commentRequest.getTimestamp(),matchingUser.getId(),matchingUser.getUsername(), matchingPost.getId());
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
        return comments.stream().map(this::mapToCommentResponse).toList();
    }
    private CommentResponse mapToCommentResponse(Comment comment){
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .timestamp(comment.getTimestamp())
                .userId(comment.getUserId())
                .username(comment.getUsername())
                .postId(comment.getPostId())
                .build();
    }
}