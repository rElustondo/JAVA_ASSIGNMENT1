package ca.gbc.friendshipservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendRequestResponse {
    private String id;
    private String senderId;
    private String receiverId;
    private String status;
    private String timestamp;
}
