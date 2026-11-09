package com.devcaleb.ead.course.services;

import com.devcaleb.ead.course.entities.Course;
import com.devcaleb.ead.course.entities.CourseUser;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface CourseUserService {
    boolean existsByCourseAndUserId(Course course, @NotNull UUID userId);

    CourseUser save(CourseUser courseUser);

    CourseUser saveAndSendSubscriptionUserInCourse(CourseUser courseUser);
}
