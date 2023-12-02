package ca.gbc.socialservice.dto;

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
    private Long senderId;
    private Long receiverId;
    private String status;
    private String timestamp;
}
