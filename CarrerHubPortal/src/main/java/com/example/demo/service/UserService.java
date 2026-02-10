package com.example.demo.service;

import java.util.Optional;

import com.example.demo.model.User;

public interface UserService {
	Optional<User> getUserByUsername(String username); 
    Optional<Integer> getUserIdByUsername(String username);
    User addUser(User user);
}