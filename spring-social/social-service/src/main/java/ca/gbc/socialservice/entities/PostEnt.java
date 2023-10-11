package ca.gbc.socialservice.entities;


import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Entity
public class PostEnt {

    @Id
    @GeneratedValue
    private Long id;
    private String content;
    private String timestamp;




}