package com.devcaleb.ead.course.services;

import com.devcaleb.ead.course.entities.Lesson;
import com.devcaleb.ead.course.entities.Module;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonService {
    Object save(Lesson lesson);

    Optional<Lesson> findLessonIntoModule(UUID moduleId, UUID lessonId);

    void delete(Lesson lesson);

    List<Lesson> findAllByModule(UUID moduleId);
}
