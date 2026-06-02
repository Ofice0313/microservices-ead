package com.devcaleb.ead.course.services.impl;

import com.devcaleb.ead.course.entities.Lesson;
import com.devcaleb.ead.course.entities.Module;
import com.devcaleb.ead.course.repositories.LessonRepository;
import com.devcaleb.ead.course.repositories.ModuleRepository;
import com.devcaleb.ead.course.services.ModuleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    LessonRepository lessonRepository;

    @Transactional
    @Override
    public void delete(Module module) {
        List<Lesson> lessons = lessonRepository.findAllLessonsIntoModule(module.getId());
        if(!lessons.isEmpty()) {
            lessonRepository.deleteAll(lessons);
        }
        moduleRepository.delete(module);
    }
}
