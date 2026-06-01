package com.devcaleb.ead.course.services.impl;

import com.devcaleb.ead.course.repositories.CourseRepository;
import com.devcaleb.ead.course.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;
}
