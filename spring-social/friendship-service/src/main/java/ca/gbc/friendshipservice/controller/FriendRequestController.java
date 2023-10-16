package ca.gbc.friendshipservice.controller;

import ca.gbc.friendshipservice.dto.FriendRequestRequest;
import ca.gbc.friendshipservice.dto.FriendRequestResponse;
import ca.gbc.friendshipservice.model.FriendRequest;
import ca.gbc.friendshipservice.service.FriendRequestServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friend-requests")
@RequiredArgsConstructor
public class FriendRequestController {
    private final FriendRequestServiceImpl friendRequestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createFriendRequest(@RequestBody FriendRequestRequest friendRequest){
        friendRequestService.createFriendRequest(friendRequest);
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
