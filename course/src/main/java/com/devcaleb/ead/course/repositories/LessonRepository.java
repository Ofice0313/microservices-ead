package com.devcaleb.ead.course.repositories;

import com.devcaleb.ead.course.entities.Lesson;
import com.devcaleb.ead.course.entities.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {

    @Query(value = "SELECT * FROM tb_lessons WHERE module_id = :moduleId", nativeQuery = true)
    List<Lesson> findAllLessonsIntoModule(@Param("moduleId") UUID moduleId);
}
