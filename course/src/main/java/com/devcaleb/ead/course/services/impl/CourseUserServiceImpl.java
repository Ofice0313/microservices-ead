package com.devcaleb.ead.course.services.impl;

import com.devcaleb.ead.course.entities.Course;
import com.devcaleb.ead.course.entities.CourseUser;
import com.devcaleb.ead.course.repositories.CourseUserRepository;
import com.devcaleb.ead.course.services.CourseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CourseUserServiceImpl implements CourseUserService {

    @Autowired
    CourseUserRepository courseUserRepository;

    @Override
    public boolean existsByCourseAndUserId(Course course, UUID userId) {
        return courseUserRepository.existsByCourseAndUserId(course, userId);
    }

    @Override
    public CourseUser save(CourseUser courseUser) {
        return courseUserRepository.save(courseUser);
    }
}
