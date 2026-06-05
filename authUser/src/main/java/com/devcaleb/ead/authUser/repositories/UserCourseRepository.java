package com.devcaleb.ead.authUser.repositories;

import com.devcaleb.ead.authUser.entities.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserCourseRepository extends JpaRepository<UserCourse, UUID> {
}
