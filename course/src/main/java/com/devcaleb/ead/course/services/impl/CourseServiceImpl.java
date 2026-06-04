package com.devcaleb.ead.course.services.impl;

import com.devcaleb.ead.course.entities.Course;
import com.devcaleb.ead.course.entities.Lesson;
import com.devcaleb.ead.course.entities.Module;
import com.devcaleb.ead.course.repositories.CourseRepository;
import com.devcaleb.ead.course.repositories.LessonRepository;
import com.devcaleb.ead.course.repositories.ModuleRepository;
import com.devcaleb.ead.course.services.CourseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    LessonRepository lessonRepository;

    @Transactional
    @Override
    public void delete(Course course) {
        List<Module> modules = moduleRepository.findAllModulesIntoCourse(course.getCourseId());
        if(!modules.isEmpty()) {
            for (Module module: modules) {
                List<Lesson> lessons = lessonRepository.findAllLessonsIntoModule(module.getModuleId());
                if(!lessons.isEmpty()) {
                    lessonRepository.deleteAll(lessons);
                }
            }
            moduleRepository.deleteAll(modules);
        }
        courseRepository.delete(course);
    }

    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Optional<Course> findById(UUID courseId) {
        return courseRepository.findById(courseId);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }
}
