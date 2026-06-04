package com.devcaleb.ead.course.services;

import com.devcaleb.ead.course.entities.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface CourseService {

    void delete(Course course);

    Course save(Course course);

    Optional<Course> findById(UUID courseId);

    Page<Course> findAll(Specification<Course> spec, Pageable pageable);
}
