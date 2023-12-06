package ca.gbc.socialservice.service;

import ca.gbc.socialservice.dto.CommentRequest;
import ca.gbc.socialservice.dto.CommentResponse;

import java.util.List;

public interface CommentService {
    String createComment(CommentRequest commentRequest);

    void deleteComment(Long productId);

    void updateComment(Long productId, CommentRequest commentRequest);

    List<CommentResponse> getAllComments();
}
