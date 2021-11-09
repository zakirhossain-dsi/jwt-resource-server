package com.example.web.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class LibraryController {

    @GetMapping(value = "/library/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getLibrary(@PathVariable("id") long id, Model model){
        return ResponseEntity.ok("Central Library");
    }

    @PostMapping(value = "/library/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createLibrary(@PathVariable("id") long id, Model model){
        return ResponseEntity.ok("Library created");
    }

    /*
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable long id, Principal principal){
        return ResponseEntity.ok(String.format("Course - %s is deleted by %s", id, principal.getName()));
    }

    @GetMapping("/greetings")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello there !!");
    }

    @GetMapping("/courses/{id}/coordinate")
    public ResponseEntity<String> coordinateStudent(@PathVariable long id, Principal principal){
        return ResponseEntity.ok(String.format("Course id - %s is being coordinated by %s", id, principal.getName()));
    }
    */
}
