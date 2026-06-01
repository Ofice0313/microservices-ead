package com.devcaleb.ead.authUser.controllers;

import com.devcaleb.ead.authUser.entities.User;
import com.devcaleb.ead.authUser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "userId")UUID userId) {
        Optional<User> userOptional = service.findById(userId);
        if(!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(userOptional.get());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId")UUID userId) {
        Optional<User> userOptional = service.findById(userId);
        if(!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        } else {
            service.delete(userOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("User deleted success!");
        }
    }
}
