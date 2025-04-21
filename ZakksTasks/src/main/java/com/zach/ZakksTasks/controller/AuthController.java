package com.zach.ZakksTasks.controller;

import com.zach.ZakksTasks.model.User;
import com.zach.ZakksTasks.repository.UserRepository;
import com.zach.ZakksTasks.security.JwtUtil;
import com.zach.ZakksTasks.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    public AuthController(UserRepository userRepository, JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists.");
        }

        user.setPassword(userDetailsServiceImpl.encodePassword(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User existingUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (userDetailsServiceImpl.verifyPassword(user.getPassword(), existingUser.getPassword())) {
            String token = jwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok(token);
        }

        return ResponseEntity.badRequest().body("Invalid credentials");
    }
}
