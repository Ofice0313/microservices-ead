package com.devcaleb.ead.course.repositories;

import com.devcaleb.ead.course.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> , JpaSpecificationExecutor<Course> {
}
