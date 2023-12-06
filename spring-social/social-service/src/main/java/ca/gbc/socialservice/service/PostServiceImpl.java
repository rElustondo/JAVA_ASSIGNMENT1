package ca.gbc.socialservice.service;

import ca.gbc.socialservice.dto.PostRequest;
import ca.gbc.socialservice.dto.PostResponse;
import ca.gbc.socialservice.dto.UserResponse;
import ca.gbc.socialservice.entities.Comment;
import ca.gbc.socialservice.model.Post;
import ca.gbc.socialservice.repository.CommentRepository;
import ca.gbc.socialservice.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final MongoTemplate mongoTemplate;
    private final WebClient webClient;

    @Value("${user.service.uri}")
    private String userApiUri;

    @Override
    public String createPost(PostRequest postRequest) {
        log.info("Creating a new post {}", postRequest.getContent());

        List<UserResponse> userResponseList = webClient
                .get()
                .uri(userApiUri)
                .retrieve()
                .bodyToFlux(UserResponse.class)
                .collectList()
                .block();

        UserResponse matchingUser = userResponseList.stream()
                .filter(user -> user.getId().equals(postRequest.getUserId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User with ID " + postRequest.getUserId() + " not found"));


        Post post = Post.builder()
                .content(postRequest.getContent())
                .timestamp(postRequest.getTimestamp())
                .userId(postRequest.getUserId())
                .username(matchingUser.getUsername())
                .build();

        postRepository.save(post);
        log.info("Post {} is saved", post.getId());
        return "Post Created Successfully";


    }

    @Override
    public void deletePost(String postId) {
        log.info("Post {} is deleted", postId);
        postRepository.deleteById(postId);
    }

    @Override
    public void updatePost(String postId, PostRequest postRequest) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(postId));
        Post post = mongoTemplate.findOne(query,Post.class);

        if(post != null){
            post.setContent(postRequest.getContent());
            post.setTimestamp(postRequest.getTimestamp());
            postRepository.save(post);
        }
    }

    @Override
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        Map<String, List<Comment>> postCommentsMap = getCommentsForPosts(); // Retrieve comments for each post
        return posts.stream()
                .map(post -> mapToProductResponse(post, postCommentsMap.getOrDefault(post.getId(), Collections.emptyList())))
                .toList();
    }
    private PostResponse mapToProductResponse(Post post, List<Comment> comments){
        List<Comment> postComments = comments.stream()
                .filter(comment -> comment.getPostId().equals(post.getId()))
                .collect(Collectors.toList());
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .timestamp(post.getTimestamp())
                .userId(post.getUserId())
                .username(post.getUsername())
                .comments(postComments)
                .build();
    }
    private Map<String, List<Comment>> getCommentsForPosts() {
        List<Comment> comments = (List<Comment>) commentRepository.findAll();
        return comments.stream()
                .collect(Collectors.groupingBy(Comment::getPostId));
    }
}
