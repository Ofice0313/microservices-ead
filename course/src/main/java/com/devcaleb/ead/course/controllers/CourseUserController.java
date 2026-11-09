package com.devcaleb.ead.course.controllers;

import com.devcaleb.ead.course.clients.AuthUserClient;
import com.devcaleb.ead.course.dto.SubscriptionDTO;
import com.devcaleb.ead.course.dto.UserDTO;
import com.devcaleb.ead.course.entities.Course;
import com.devcaleb.ead.course.entities.CourseUser;
import com.devcaleb.ead.course.enums.UserStatus;
import com.devcaleb.ead.course.services.CourseService;
import com.devcaleb.ead.course.services.CourseUserService;
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
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/coursesUsers")
public class CourseUserController {

    @Autowired
    AuthUserClient authUserClient;

    @Autowired
    CourseService courseService;

    @Autowired
    CourseUserService courseUserService;

    @GetMapping("/{courseId}/users")
    public ResponseEntity<Page<UserDTO>> getAllUserByCourse(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable, @PathVariable(value = "courseId") UUID courseId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(authUserClient.getAllUsersByCourse(courseId, pageable));
    }

    @PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(
            @PathVariable(value = "courseId") UUID courseId,
            @RequestBody @Valid SubscriptionDTO subscriptionDTO
    ) {
        ResponseEntity<UserDTO> responseUser;

        Optional<Course> courseOptional = courseService.findById(courseId);
        if(!courseOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found!");
        }
        if(courseUserService.existsByCourseAndUserId(courseOptional.get(), subscriptionDTO.getUserId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERROR: subscription already exists!");
        }
        // verification user
        try {
            responseUser = authUserClient.getOneUserById(subscriptionDTO.getUserId());
            if(responseUser.getBody().getUserStatus().equals(UserStatus.BLOCKED)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User is blocked.");
            }
        } catch (HttpStatusCodeException e) {
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
        }
        CourseUser courseUser = courseUserService.saveAndSendSubscriptionUserInCourse(courseOptional.get().convertToCourseUser(subscriptionDTO.getUserId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(courseUser);
    }
}
