package ca.gbc.friendshipservice.controller;

import ca.gbc.friendshipservice.dto.FriendRequestRequest;
import ca.gbc.friendshipservice.dto.FriendRequestResponse;
import ca.gbc.friendshipservice.model.FriendRequest;
import ca.gbc.friendshipservice.service.FriendRequestServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/friend-requests")
@RequiredArgsConstructor
@Slf4j
public class FriendRequestController {
    private final FriendRequestServiceImpl friendRequestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name="friend",fallbackMethod = "createFriendRequestFallback")
    @TimeLimiter(name="friend")
    @Retry(name = "friend")
    public CompletableFuture<String> createFriendRequest(@RequestBody FriendRequestRequest friendRequest){
        return CompletableFuture.supplyAsync(()-> friendRequestService.createFriendRequest(friendRequest));
    }
    public CompletableFuture<String> createFriendRequestFallback(FriendRequestRequest request,RuntimeException e){
        log.error("exception is: {}", e.getMessage());
        //return "Fallback invoked: Order placed failed, Please try again later";
        return CompletableFuture.supplyAsync(()-> "Fallback invoked: Create Friend Request failed, Please try again later");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FriendRequestResponse> getAllFriendRequests(){
        return friendRequestService.getAllFriendRequests();
    }

    @PutMapping({"/{friendRequestId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateFriendRequest(@PathVariable("friendRequestId") String friendRequestId, @RequestBody FriendRequestRequest friendRequestRequest) {
        friendRequestService.updateFriendRequest(friendRequestId, friendRequestRequest);
    }
    @DeleteMapping("/{friendRequestId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable("friendRequestId") String friendRequestId){
        friendRequestService.deleteFriendRequest(friendRequestId);
    }

}
