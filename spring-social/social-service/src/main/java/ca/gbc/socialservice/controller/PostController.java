package ca.gbc.socialservice.controller;


import ca.gbc.socialservice.dto.PostRequest;
import ca.gbc.socialservice.dto.PostResponse;
import ca.gbc.socialservice.dto.UserRequest;
import ca.gbc.socialservice.service.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostServiceImpl postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(@RequestBody PostRequest postRequest){
        postService.createPost(postRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponse> getAllPosts(){
        return postService.getAllPosts();
    }
    @PutMapping({"/{postId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePost(@PathVariable("postId") String postId, @RequestBody PostRequest postRequest) {
        postService.updatePost(postId, postRequest);
    }
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable("postId") String postId){
        postService.deletePost(postId);
    }
}
