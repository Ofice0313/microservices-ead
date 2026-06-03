package com.devcaleb.ead.course.services;

import com.devcaleb.ead.course.entities.Module;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleService {

    void delete(Module module);

    Object save(Module module);

    Optional<Module> findModuleIntoCourse(UUID courseId, UUID moduleId);

    List<Module> findAll();

    List<Module> findAllByCourse(UUID courseId);
}
