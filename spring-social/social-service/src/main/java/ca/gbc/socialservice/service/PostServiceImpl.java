package ca.gbc.socialservice.service;

import ca.gbc.socialservice.dto.PostRequest;
import ca.gbc.socialservice.dto.PostResponse;
import ca.gbc.socialservice.entities.Post;
import ca.gbc.socialservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final MongoTemplate mongoTemplate;
    @Override
    public void createPost(PostRequest postRequest) {
        log.info("Creating a new post {}", postRequest.getContent());

        Post post = Post.builder()
                .content(postRequest.getContent())
                .timestamp(postRequest.getTimestamp())
                .build();

        postRepository.save(post);

        log.info("Post {} is saved", post.getId());

    }

    @Override
    public void deleteProduct(String productId) {
        log.info("Post {} is deleted", productId);
        postRepository.deleteById(productId);
    }

    @Override
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapToProductResponse).toList();
    }
    private PostResponse mapToProductResponse(Post post){
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .timestamp(post.getTimestamp())
                .build();
    }
}
