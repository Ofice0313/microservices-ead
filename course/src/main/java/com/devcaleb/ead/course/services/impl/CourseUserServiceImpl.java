package com.devcaleb.ead.course.services.impl;

import com.devcaleb.ead.course.repositories.CourseUserRepository;
import com.devcaleb.ead.course.services.CourseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseUserServiceImpl implements CourseUserService {

    @Autowired
    CourseUserRepository courseUserRepository;
}
