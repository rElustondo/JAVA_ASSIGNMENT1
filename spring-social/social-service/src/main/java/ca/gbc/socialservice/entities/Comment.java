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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEnt user;

    // Getters and setters
}