package com.devcaleb.ead.course.repositories;

import com.devcaleb.ead.course.entities.CourseUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseUserRepository extends JpaRepository<CourseUser, UUID> {
}
