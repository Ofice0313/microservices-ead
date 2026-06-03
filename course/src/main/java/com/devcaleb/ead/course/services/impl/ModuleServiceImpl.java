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
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public Object save(Module module) {
        return moduleRepository.save(module);
    }

    @Override
    public Optional<Module> findModuleIntoCourse(UUID courseId, UUID moduleId) {
        return moduleRepository.findModuleIntoCourse(courseId, moduleId);
    }

    @Override
    public List<Module> findAll() {
        return List.of();
    }

    @Override
    public List<Module> findAllByCourse(UUID courseId) {
        return moduleRepository.findAllModulesIntoCourse(courseId);
    }
}
