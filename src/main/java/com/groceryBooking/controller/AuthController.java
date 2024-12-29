package com.groceryBooking.controller;

import com.groceryBooking.ValidatingException;
import com.groceryBooking.config.JwtUtil;
import com.groceryBooking.entity.Role;
import com.groceryBooking.entity.User;
import com.groceryBooking.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);
        userRepository.save(user);

        return "user created";

    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");
        User user = userRepository.findByUsername(username);

        if(user == null) {
            throw new ValidatingException("Incorrect username", HttpStatus.UNAUTHORIZED);
        }
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new ValidatingException("Incorrect password", HttpStatus.UNAUTHORIZED);
        }

        return jwtUtil.generateToken(username, user.getRole().name());
    }

    @GetMapping
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable Long id) {
        if(!userRepository.existsById(id)) {
            throw new ValidatingException("User not found", HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

}
