package ca.gbc.socialservice;

import ca.gbc.socialservice.entities.UserEnt;
import ca.gbc.socialservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class SocialServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(SocialServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(UserRepository repository){
        return (args -> {
            insertUserData(repository);
            System.out.println(repository.findAll());
        });
    }
    private void insertUserData(UserRepository repository){
        UserEnt user1 = new UserEnt("Tim1","tim1@abc.ca","12345");
        System.out.println(user1.getId());
        System.out.println(user1.getEmail());
        UserEnt user2 = new UserEnt("Tim2","tim2@abc.ca","asd123");
        System.out.println(user2.getId());
        System.out.println(user2.getEmail());
        repository.save(user1);
        repository.save(user2);

    }
}
