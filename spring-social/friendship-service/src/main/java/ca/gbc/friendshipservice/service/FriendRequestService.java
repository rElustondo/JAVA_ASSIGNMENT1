package ca.gbc.friendshipservice.service;

import ca.gbc.friendshipservice.dto.FriendRequestRequest;
import ca.gbc.friendshipservice.dto.FriendRequestResponse;

import java.util.List;

public interface FriendRequestService {
    void createFriendRequest(FriendRequestRequest friendRequestRequest);

    void deleteFriendRequest(String friendRequestId);

    void updateFriendRequest(String friendRequestId, FriendRequestRequest friendRequestRequest);
    List<FriendRequestResponse> getAllFriendRequests();
}
