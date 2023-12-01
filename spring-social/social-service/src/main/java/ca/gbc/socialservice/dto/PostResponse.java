package ca.gbc.socialservice.dto;

import ca.gbc.socialservice.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
    private String id;
    private String content;
    private String timestamp;
    private Long userId;
    private String username;
    private List<Comment> comments = new ArrayList<>();
}
