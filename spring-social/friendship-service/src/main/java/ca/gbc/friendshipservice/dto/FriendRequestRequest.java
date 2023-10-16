package ca.gbc.friendshipservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendRequestRequest {
    private String senderId;
    private String receiverId;
    private String status;
    private String timestamp;
}

