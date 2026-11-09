package com.devcaleb.ead.authUser.controllers;

import com.devcaleb.ead.authUser.clients.CourseClient;
import com.devcaleb.ead.authUser.dto.CourseDTO;
import com.devcaleb.ead.authUser.dto.UserCourseDTO;
import com.devcaleb.ead.authUser.entities.User;
import com.devcaleb.ead.authUser.entities.UserCourse;
import com.devcaleb.ead.authUser.services.UserCourseService;
import com.devcaleb.ead.authUser.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/users")
public class UserCourseController {

    @Autowired
    CourseClient courseClient;

    @Autowired
    UserService userService;

    @Autowired
    UserCourseService userCourseService;

    @GetMapping("/{userId}/courses")
    public ResponseEntity<Page<CourseDTO>> getAllCoursesByUser(
            @PageableDefault(page = 0, size = 10, sort = "courseId", direction = Sort.Direction.ASC)
            Pageable pageable, @PathVariable(value = "userId") UUID userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(courseClient.getAllCoursesByUser(userId, pageable));
    }

    @PostMapping("/{userId}/courses/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(
            @PathVariable(value = "userId") UUID userId,
            @RequestBody @Valid UserCourseDTO userCourseDTO
    ) {
        Optional<User> userOptional = userService.findById(userId);
        if(!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }
        if(userCourseService.existsByUserAndCourseId(userOptional.get(), userCourseDTO.getCourseId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERROR: subscription already exists!");
        }
        UserCourse userCourse = userCourseService.save(userOptional.get().convertToUserCourse(userCourseDTO.getCourseId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(userCourse);
    }
}
