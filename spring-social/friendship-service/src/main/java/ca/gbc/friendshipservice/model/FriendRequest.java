package ca.gbc.friendshipservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(value = "friendRequest")
public class FriendRequest {

    @Id
    @Getter
    private String id;
    @Getter
    @Setter
    private String senderId;
    @Getter
    @Setter
    private String receiverId;
    @Getter
    @Setter
    private String status;
    @Getter
    @Setter
    private String timestamp;

}
