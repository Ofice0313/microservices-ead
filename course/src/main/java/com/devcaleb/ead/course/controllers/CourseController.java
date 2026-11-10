package com.devcaleb.ead.course.controllers;

import com.devcaleb.ead.course.dto.CourseDTO;
import com.devcaleb.ead.course.entities.Course;
import com.devcaleb.ead.course.services.CourseService;
import com.devcaleb.ead.course.specifications.SpecificationTemplate;
import com.devcaleb.ead.course.validations.CourseValidator;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping(value = "/api/courses")
@CrossOrigin(origins = "*", maxAge = 36000)
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    CourseValidator courseValidator;

    @PostMapping
    public ResponseEntity<Object> saveCourse(@RequestBody CourseDTO courseDTO, Errors errors) {
        log.debug("POST saveCourse courseDTO received {} ", courseDTO.toString());
        courseValidator.validate(courseDTO, errors);
        if(errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
        }
        var course = new Course();
        BeanUtils.copyProperties(courseDTO, course);
        course.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        course.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        log.debug("POST saveCourse course saved {} ", course.toString());
        log.info("Course saved successfully courseId {} ", course.getCourseId());
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(course));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "courseId")UUID courseId) {
        log.debug("DELETE deleteCourse courseId received {} ", courseId);
        Optional<Course> courseOptional = courseService.findById(courseId);
        if(!courseOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found!");
        }
        log.debug("DELETE deleteCourse courseId deleted {} ", courseId);
        log.info("Course deleted successfully courseId {} ", courseId);
        courseService.delete(courseOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Course deleted successfully!");
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Object> update(
            @PathVariable(value = "courseId")UUID courseId,
            @RequestBody @Valid CourseDTO courseDTO
            ) {
        Optional<Course> courseOptional = courseService.findById(courseId);
        if(!courseOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found!");
        }
        var course = courseOptional.get();
        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        course.setImageUrl(courseDTO.getImageUrl());
        course.setCourseStatus(courseDTO.getCourseStatus());
        course.setCourseLevel(courseDTO.getCourseLevel());
        course.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.OK).body(courseService.save(course));
    }

    @GetMapping
    public ResponseEntity<Page<Course>> getAllCourses(
            SpecificationTemplate.CourseSpec spec,
            @PageableDefault(page = 0, size = 10, sort = "courseId", direction = Sort.Direction.ASC)
            Pageable pageable, @RequestParam(required = false) UUID userId) {
        if(userId != null) {
            return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll(SpecificationTemplate.courseUserId(userId).and(spec), pageable));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll(spec, pageable));
        }

    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Object> getOneCourse(@PathVariable(value = "courseId")UUID courseId) {
        Optional<Course> courseOptional = courseService.findById(courseId);
        if(!courseOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(courseOptional.get());
    }
}
