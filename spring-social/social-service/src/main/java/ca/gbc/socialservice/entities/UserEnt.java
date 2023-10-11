package ca.gbc.socialservice.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Entity
public class UserEnt {
    @Id
    private Long id;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String email;

    public UserEnt(String username, String email){
        this.id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        this.username = username;
        this.email = email;
    }
    public UserEnt(){

    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;
}
