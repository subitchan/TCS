package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.GeminiResponse;

import com.example.demo.serviceImpl.GeminiService;

@RestController
@RequestMapping("/api")
public class ChatController {

    @Autowired
    private GeminiService geminiService;

   

    @PostMapping("/chat")
    public ResponseEntity<GeminiResponse> chat(@RequestBody String userMessage) {
        GeminiResponse response = geminiService.sendToGemini(userMessage);
        return ResponseEntity.ok(response);
    }


}

