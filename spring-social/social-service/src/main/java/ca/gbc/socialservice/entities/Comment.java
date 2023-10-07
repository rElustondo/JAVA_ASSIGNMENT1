package ca.gbc.socialservice.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
    private String content;
    private String timestamp;
    // Other comment properties

    @ManyToOne
    private PostEnt post;

    @ManyToOne
    private UserEnt user;

    // Getters and setters
}