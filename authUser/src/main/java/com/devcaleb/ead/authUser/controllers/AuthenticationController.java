package com.devcaleb.ead.authUser.controllers;

import com.devcaleb.ead.authUser.dto.UserDTO;
import com.devcaleb.ead.authUser.entities.User;
import com.devcaleb.ead.authUser.enums.UserStatus;
import com.devcaleb.ead.authUser.enums.UserType;
import com.devcaleb.ead.authUser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/auth")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(
            @RequestBody @JsonView(UserDTO.UserView.RegistrationPost.class) UserDTO userDTO) {
        if(userService.existsByUsername(userDTO.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is already Taken!");
        }
        if(userService.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is already Taken!");
        }
        var user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setUserStatus(UserStatus.ACTIVE);
        user.setUserType(UserType.STUDENT);
        user.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
