package ca.gbc.friendshipservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendRequestRequest {
    private Long senderId;
    private Long receiverId;
    private String status;
    private String timestamp;
}

