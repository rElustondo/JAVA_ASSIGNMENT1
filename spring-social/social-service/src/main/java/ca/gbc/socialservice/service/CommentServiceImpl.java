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
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    @Override
    public void createComment(CommentRequest commentRequest) {
        Comment newUser = new Comment(commentRequest.getContent(),commentRequest.getTimestamp());
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
                .build();
    }
}