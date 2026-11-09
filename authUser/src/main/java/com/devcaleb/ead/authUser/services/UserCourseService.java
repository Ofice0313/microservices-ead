package com.devcaleb.ead.authUser.services;

import com.devcaleb.ead.authUser.entities.User;
import com.devcaleb.ead.authUser.entities.UserCourse;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface UserCourseService {
    boolean existsByUserAndCourseId(User user, @NotNull UUID courseId);

    UserCourse save(UserCourse userCourse);
}
