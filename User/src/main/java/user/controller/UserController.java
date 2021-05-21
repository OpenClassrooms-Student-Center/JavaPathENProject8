package user.controller;

import org.springframework.web.bind.annotation.*;
import user.model.User;
import user.service.UserService;
import user.service.UserServiceInterface;

import java.util.ArrayList;

@RestController
public class UserController {

    private UserServiceInterface userServiceInterface;

    public UserController() {
        userServiceInterface = new UserService();
    }

    public UserController(UserServiceInterface userServiceInterface) {
        this.userServiceInterface = userServiceInterface;
    }

    @PostMapping(value = "/user/create")
    public void createUser(User user) {
        userServiceInterface.createUser(user);
    }

    @GetMapping("/user/read")
    public User readUser(String userName) {
        return userServiceInterface.readUser(userName);
    }

    @PutMapping(value = "/user/update")
    public void updateUser(String userName, User user) {
        userServiceInterface.updateUser(userName, user);
    }

    @DeleteMapping(value = "/user/delete")
    public void deleteUser(String userName) {
        userServiceInterface.deleteUser(userName);
    }
}