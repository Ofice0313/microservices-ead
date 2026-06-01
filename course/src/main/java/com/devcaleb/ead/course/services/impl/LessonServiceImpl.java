package com.devcaleb.ead.course.services.impl;

import com.devcaleb.ead.course.repositories.LessonRepository;
import com.devcaleb.ead.course.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    LessonRepository lessonRepository;
}
