package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/users")

public class UserController {

    @Autowired
    private UserService userService;

  
    
    //  GET user ID by username
    @GetMapping("/findIdByName/{username}")
    public ResponseEntity<Integer> getUserIdByUsername(@PathVariable String username) {
        return userService.getUserIdByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
