package ca.gbc.socialservice.service;

import ca.gbc.socialservice.dto.PostRequest;
import ca.gbc.socialservice.dto.PostResponse;

import java.util.List;

public interface PostService {

    String createPost(PostRequest postRequest);

    void deletePost(String postId);

    void updatePost(String postId, PostRequest postRequest);
    List<PostResponse> getAllPosts();

}
