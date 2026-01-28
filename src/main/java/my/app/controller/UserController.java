package my.app.controller;

import my.app.model.User;
import my.app.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Add new user
    @PostMapping // It will work when we send the data (JSON)
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}
