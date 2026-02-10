package com.example.demo.controller;

import com.example.demo.model.EmployerProfile;
import com.example.demo.model.JobSeekerProfile;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.EmployerProfileService;
import com.example.demo.service.JobSeekerProfileService;
import com.example.demo.service.UserService;
import com.example.demo.security.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
 // ✅ Applies to all /auth/** endpoints

public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> req) {
        String username = req.get("username");
        String password = req.get("password");

        authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        User user = userRepo.findByUsername(username).orElseThrow();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String token = jwtUtil.generateToken(username, user.getRole());

        return ResponseEntity.ok(Map.of("token", token, "role", user.getRole()));
    }
    
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User createdUser = userService.addUser(user);
        return ResponseEntity.ok(createdUser);
    }
    
    @Autowired
    private EmployerProfileService employerService;

    
    @PostMapping("/addEmployer")
    public ResponseEntity<EmployerProfile> addEmployer(@RequestBody EmployerProfile employer) {
        return ResponseEntity.ok(employerService.addEmployer(employer));
    }
    
    @Autowired
    private JobSeekerProfileService jobSeekerService;

    @PostMapping("/addJobSeeker")
    public ResponseEntity<JobSeekerProfile> addJobSeeker(@RequestBody JobSeekerProfile jobSeeker) {
        return ResponseEntity.ok(jobSeekerService.addJobSeeker(jobSeeker));
    }
    

}
