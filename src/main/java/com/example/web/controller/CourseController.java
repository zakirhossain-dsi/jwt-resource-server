package com.example.web.controller;

import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {

    @GetMapping(value = "/courses/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCourse(@PathVariable("id") long id, Model model){
        return "ICT";
    }

    @DeleteMapping("/courses/{id}")
    public void deleteStudent(@PathVariable long id){
        System.out.println("Deleted");
    }

    @GetMapping("/greetings")
    public String sayHello() {
        return "Hello";
    }
}
