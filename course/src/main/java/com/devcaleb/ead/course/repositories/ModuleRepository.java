package com.devcaleb.ead.course.repositories;

import com.devcaleb.ead.course.entities.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ModuleRepository extends JpaRepository<Module, UUID> {

//    @EntityGraph(attributePaths = {"course"})
//    Module findByTitle(String title);

    @Query(value = "SELECT * FROM tb_modules WHERE course_id = :courseId", nativeQuery = true)
    List<Module> findAllModulesIntoCourse(@Param("courseId") UUID courseId);
}
