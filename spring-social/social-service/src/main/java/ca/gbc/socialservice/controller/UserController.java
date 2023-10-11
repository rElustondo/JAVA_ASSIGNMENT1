package ca.gbc.socialservice.controller;

import ca.gbc.socialservice.dto.*;
import ca.gbc.socialservice.service.PostServiceImpl;
import ca.gbc.socialservice.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserRequest userRequest){
        userService.createUser(userRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }
    @PutMapping({"/{userId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable("userId") Long userId, @RequestBody UserRequest userRequest) {
        userService.updateUser(userId, userRequest);
    }
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("userId") Long userId){
        userService.deleteUser(userId);
    }
}
