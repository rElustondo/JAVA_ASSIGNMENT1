package ca.gbc.friendshipservice.service;

import ca.gbc.friendshipservice.dto.FriendRequestRequest;
import ca.gbc.friendshipservice.dto.FriendRequestResponse;
import ca.gbc.friendshipservice.model.FriendRequest;
import ca.gbc.friendshipservice.repository.FriendRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendRequestServiceImpl implements  FriendRequestService{
    private final FriendRequestRepository friendRequestRepository;
    private final MongoTemplate mongoTemplate;
    @Override
    public void createFriendRequest(FriendRequestRequest friendRequestRequest) {
        FriendRequest friendRequest = FriendRequest.builder()
                .senderId(friendRequestRequest.getSenderId())
                .receiverId(friendRequestRequest.getReceiverId())
                .status(friendRequestRequest.getStatus())
                .timestamp(friendRequestRequest.getTimestamp())
                .build();
        friendRequestRepository.save(friendRequest);

    }

    @Override
    public void deleteFriendRequest(String friendRequestId) {
        friendRequestRepository.deleteById(friendRequestId);
    }

    @Override
    public void updateFriendRequest(String friendRequestId, FriendRequestRequest friendRequestRequest) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(friendRequestId));
        FriendRequest friendRequest = mongoTemplate.findOne(query,FriendRequest.class);
        if(friendRequest != null){
            friendRequest.setStatus(friendRequestRequest.getStatus());
            friendRequest.setTimestamp(friendRequestRequest.getTimestamp());
            friendRequestRepository.save(friendRequest);
        }
    }

    @Override
    public List<FriendRequestResponse> getAllFriendRequests() {
        List<FriendRequest> friendRequests = friendRequestRepository.findAll();
        return friendRequests.stream().map(this::mapToFriendRequestResponse).toList();
    }
    private FriendRequestResponse mapToFriendRequestResponse(FriendRequest friendRequest){
        return FriendRequestResponse.builder()
                .id(friendRequest.getId())
                .senderId(friendRequest.getSenderId())
                .receiverId(friendRequest.getReceiverId())
                .status(friendRequest.getStatus())
                .timestamp(friendRequest.getTimestamp())
                .build();
    }
}
