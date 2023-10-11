package ca.gbc.socialservice.controller;

import ca.gbc.socialservice.dto.CommentRequest;
import ca.gbc.socialservice.dto.CommentResponse;
import ca.gbc.socialservice.dto.UserRequest;
import ca.gbc.socialservice.dto.UserResponse;
import ca.gbc.socialservice.service.CommentServiceImpl;
import ca.gbc.socialservice.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceImpl commentService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createComment(@RequestBody CommentRequest commentRequest){
        commentService.createComment(commentRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponse> getAllComments(){
        return commentService.getAllComments();
    }
    @PutMapping({"/{commentId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateComment(@PathVariable("commentId") Long commentId, @RequestBody CommentRequest commentRequest) {
        commentService.updateComment(commentId, commentRequest);
    }
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("commentId") Long commentId){
        commentService.deleteComment(commentId);
    }
}
