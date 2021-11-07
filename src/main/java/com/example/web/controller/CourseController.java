package com.example.web.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class CourseController {

    @GetMapping(value = "/courses/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCourse(@PathVariable("id") long id, Model model){
        return "ICT";
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable long id, Principal principal){
        return ResponseEntity.ok(String.format("Course - %s is deleted by %s", id, principal.getName()));
    }

    @GetMapping("/greetings")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello there!!");
    }

    @GetMapping("/courses/{id}/coordinate")
    public ResponseEntity<String> coordinateStudent(@PathVariable long id, Principal principal){
        return ResponseEntity.ok(String.format("Course id - %s is being coordinated by %s", id, principal.getName()));
    }

    @GetMapping("/customers")
    public ResponseEntity<String> getCustomers(){
        return ResponseEntity.ok("Hello");
    }

}
