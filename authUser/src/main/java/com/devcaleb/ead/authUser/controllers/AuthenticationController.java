package com.devcaleb.ead.authUser.controllers;

import com.devcaleb.ead.authUser.dto.UserDTO;
import com.devcaleb.ead.authUser.entities.User;
import com.devcaleb.ead.authUser.enums.UserStatus;
import com.devcaleb.ead.authUser.enums.UserType;
import com.devcaleb.ead.authUser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/auth")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(
            @RequestBody @Validated(UserDTO.UserView.RegistrationPost.class)
            @JsonView(UserDTO.UserView.RegistrationPost.class) UserDTO userDTO) {
        log.debug("POST registerUser userDto received {} ", userDTO.toString());
        if(userService.existsByUsername(userDTO.getUsername())) {
            log.warn("Username {} is already Taken!", userDTO.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is already Taken!");
        }
        if(userService.existsByEmail(userDTO.getEmail())) {
            log.warn("Email {} is already Taken!", userDTO.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is already Taken!");
        }
        var user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setUserStatus(UserStatus.ACTIVE);
        user.setUserType(UserType.STUDENT);
        user.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userService.save(user);
        log.debug("POST registerUser userId saved {} ", user.getId());
        log.info("User saved successfully userId {} ", user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/logger")
    public String index() {
        log.trace("TRACE");
        log.debug("DEBUG");
        log.info("INFO");
        log.warn("WARN");
        log.error("ERROR");
        return "Logging Spring boot...";
    }
}
