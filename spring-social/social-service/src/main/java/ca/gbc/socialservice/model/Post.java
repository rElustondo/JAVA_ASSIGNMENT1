package ca.gbc.socialservice.model;
import ca.gbc.socialservice.entities.Comment;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(value = "post")

public class Post {

    @Id
    private String id;
    private String content;
    private String timestamp;
    private Long userId;
    private String username;
    private List<Comment> comments = new ArrayList<>();


}
