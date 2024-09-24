package com.example.springsecurityex2.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurityex2.Entity.Student;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class StudentController {

    List<Student>  students = new ArrayList<>(List.of(
        new Student(1, "John", 10),
        new Student(2,"Yash",20),
        new Student(3,"Rahul",30)
    ));

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return students;
    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request)
    {
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @PostMapping("/students")
    public Student createStudent(@RequestBody Student student) {
        students.add(student);
        return student;
    }


    
}
