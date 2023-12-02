package ca.gbc.socialservice.entities;
import ca.gbc.socialservice.dto.FriendRequestResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Entity
public class UserEnt {
    @Id
    @Getter
    private Long id;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String password;



    public UserEnt(String username, String email, String password){
        this.id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public UserEnt(){

    }


}
