package com.example.springsecurityex2.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurityex2.Entity.User;
import com.example.springsecurityex2.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getUser(){
        List<User> users = userService.getAllUsers();
        return users;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user)
    {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user)
    {
        return userService.verify(user);
    }
    
}
