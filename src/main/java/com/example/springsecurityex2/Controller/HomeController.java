package com.example.springsecurityex2.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HomeController {
    
    @GetMapping("/")
    public String greet(HttpServletRequest request)
    {
        return "Hello, Learners!" + request.getSession().getId();
    }
}
