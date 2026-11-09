package com.devcaleb.ead.authUser.services.impl;

import com.devcaleb.ead.authUser.entities.User;
import com.devcaleb.ead.authUser.entities.UserCourse;
import com.devcaleb.ead.authUser.repositories.UserCourseRepository;
import com.devcaleb.ead.authUser.services.UserCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserCourseServiceImpl implements UserCourseService {

    @Autowired
    UserCourseRepository userCourseRepository;


    @Override
    public boolean existsByUserAndCourseId(User user, UUID courseId) {
        return userCourseRepository.existsByUserAndCourseId(user, courseId);
    }

    @Override
    public UserCourse save(UserCourse userCourse) {
        return userCourseRepository.save(userCourse);
    }
}
