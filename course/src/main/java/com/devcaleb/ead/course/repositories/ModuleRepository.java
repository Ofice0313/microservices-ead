package com.devcaleb.ead.course.repositories;

import com.devcaleb.ead.course.entities.Course;
import com.devcaleb.ead.course.entities.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ModuleRepository extends JpaRepository<Module, UUID> {
}
