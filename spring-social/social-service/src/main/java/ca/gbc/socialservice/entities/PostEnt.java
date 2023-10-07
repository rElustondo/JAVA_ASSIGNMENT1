package ca.gbc.socialservice.entities;


import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Entity
public class PostEnt {

    @Id
    @GeneratedValue
    private Long id;
    private String content;
    private String timestamp;

    @DBRef
    @ManyToOne
    private UserEnt user;

}