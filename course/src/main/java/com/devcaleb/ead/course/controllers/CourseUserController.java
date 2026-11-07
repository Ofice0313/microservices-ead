package com.devcaleb.ead.course.controllers;

import com.devcaleb.ead.course.clients.CourseClient;
import com.devcaleb.ead.course.dto.UserDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/coursesUsers")
public class CourseUserController {

    @Autowired
    CourseClient courseClient;

    @GetMapping("/{courseId}/users")
    public ResponseEntity<Page<UserDTO>> getAllUserByCourse(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable, @PathVariable(value = "courseId") UUID courseId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(courseClient.getAllUsersByCourse(courseId, pageable));
    }
}
