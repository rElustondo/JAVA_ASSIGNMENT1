package ca.gbc.socialservice.entities;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Entity
public class Comment {

    @Id
    private Long id;
    @Getter
    @Setter
    private String content;
    @Getter
    @Setter
    private String timestamp;

    private Long user_id;
    @Getter
    @Setter
    private String username;
    // Other comment properties

    public Comment(String content, String timestamp,Long userId, String username){
        this.id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        this.content = content;
        this.timestamp = timestamp;
        this.user_id = userId;
        this.username = username;
    }
    public Comment(){

    }
    public Long getId() {
        return id;
    }
    public Long getUserId() {
        return user_id;
    }
    public void setUserId(Long userId) {
         user_id = userId;
    }

}