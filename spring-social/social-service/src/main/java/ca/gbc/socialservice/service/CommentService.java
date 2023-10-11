package ca.gbc.socialservice.service;

import ca.gbc.socialservice.dto.CommentRequest;
import ca.gbc.socialservice.dto.CommentResponse;

import java.util.List;

public interface CommentService {
    void createComment(CommentRequest commentRequest);

    void deleteComment(Long productId);

    List<CommentResponse> getAllComments();
}
