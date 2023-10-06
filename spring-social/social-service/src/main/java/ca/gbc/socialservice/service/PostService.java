package ca.gbc.socialservice.service;

import ca.gbc.socialservice.dto.PostRequest;
import ca.gbc.socialservice.dto.PostResponse;

import java.util.List;

public interface PostService {

    void createPost(PostRequest postRequest);

    void deleteProduct(String productId);

    List<PostResponse> getAllPosts();

}
