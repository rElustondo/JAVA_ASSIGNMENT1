package ca.gbc.socialservice.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity
public class Comment {

    @Id
    private Long id;
    @Getter
    private String content;
    @Getter
    private String timestamp;
    // Other comment properties

    public Comment(String content, String timestamp){
        this.id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        this.content = content;
        this.timestamp = timestamp;
    }
    public Comment(){

    }
    public Long getId() {
        return id;
    }
//    @ManyToOne
//    private PostEnt post;
//
//    @ManyToOne
//    private UserEnt user;

    // Getters and setters
}