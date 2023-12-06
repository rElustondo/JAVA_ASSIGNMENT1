package ca.gbc.socialservice.controller;

import ca.gbc.socialservice.dto.*;
import ca.gbc.socialservice.service.PostServiceImpl;
import ca.gbc.socialservice.service.UserServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserServiceImpl userService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createUser(@RequestBody UserRequest userRequest){
        return userService.createUser(userRequest);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @CircuitBreaker(name="social",fallbackMethod = "getUserFallback")
    @TimeLimiter(name="social")
    @Retry(name = "social")
    public CompletableFuture<List<UserResponse>> getAllUsers(){
        return CompletableFuture.supplyAsync(()-> userService.getAllUsers());

    }
    public CompletableFuture<List<UserResponse>> getUserFallback(RuntimeException e) {
        log.error("exception is: {}", e.getMessage());
        CompletableFuture<List<UserResponse>> future = new CompletableFuture<>();
        future.completeExceptionally(new RuntimeException("Fallback invoked: Get User failed, Please try again later"));
        return future;
    }

    @PutMapping({"/{userId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateComment(@PathVariable("userId") Long userId, @RequestBody UserRequest userRequest) {
        userService.updateUser(userId, userRequest);
    }
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("userId") Long userId){
        userService.deleteUser(userId);
    }
}
