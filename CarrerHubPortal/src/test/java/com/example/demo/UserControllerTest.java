package com.example.demo;

import com.example.demo.controller.UserController;
import com.example.demo.security.CustomUserDetailsService;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void testGetUserIdByUsername_Found() throws Exception {
        Mockito.when(userService.getUserIdByUsername("john")).thenReturn(Optional.of(10));

        mockMvc.perform(get("/users/findIdByName/john"))
               .andExpect(status().isOk())
               .andExpect(content().string("10"));
    }

    @Test
    void testGetUserIdByUsername_NotFound() throws Exception {
        Mockito.when(userService.getUserIdByUsername("unknown")).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/findIdByName/unknown"))
               .andExpect(status().isNotFound());
    }
}
