package com.devcaleb.ead.course.controllers;

import com.devcaleb.ead.course.dto.CourseDTO;
import com.devcaleb.ead.course.dto.ModuleDTO;
import com.devcaleb.ead.course.entities.Course;
import com.devcaleb.ead.course.entities.Module;
import com.devcaleb.ead.course.services.CourseService;
import com.devcaleb.ead.course.services.ModuleService;
import com.devcaleb.ead.course.specifications.SpecificationTemplate;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/modules")
@CrossOrigin(origins = "*", maxAge = 36000)
public class ModuleController {

    @Autowired
    ModuleService moduleService;

    @Autowired
    CourseService courseService;

    @PostMapping("/courses/{courseId}/module")
    public ResponseEntity<Object> saveModule(
            @PathVariable(value = "courseId") UUID courseId,
            @RequestBody @Valid ModuleDTO moduleDTO) {
        Optional<Course> courseOptional = courseService.findById(courseId);
        if(!courseOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found!");
        }
        var module = new Module();
        BeanUtils.copyProperties(moduleDTO, module);
        module.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        module.setCourse(courseOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.save(module));
    }

    @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "courseId")UUID courseId,
                                         @PathVariable(value = "moduleId")UUID moduleId
                                         ) {
        Optional<Module> moduleOptional = moduleService.findModuleIntoCourse(courseId, moduleId);
        if(!moduleOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module Not Found for this course!");
        }
        moduleService.delete(moduleOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Module deleted successfully!");
    }

    @PutMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> update(
            @PathVariable(value = "courseId")UUID courseId,
            @PathVariable(value = "moduleId")UUID moduleId,
            @RequestBody @Valid ModuleDTO moduleDTO
            ) {
        Optional<Module> moduleOptional = moduleService.findModuleIntoCourse(courseId, moduleId);
        if(!moduleOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module Not Found for this course!");
        }
        var module = moduleOptional.get();
        module.setTitle(moduleDTO.getTitle());
        module.setDescription(moduleDTO.getDescription());
        return ResponseEntity.status(HttpStatus.OK).body(moduleService.save(module));
    }

    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<Page<Module>> getAllModules(
            @PathVariable(value = "courseId")UUID courseId,
            SpecificationTemplate.ModuleSpec spec,
            @PageableDefault(page = 0, size = 10, sort = "moduleId", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(moduleService.findAllByCourse(SpecificationTemplate.moduleCourseId(courseId).and(spec), pageable));
    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> getOneModule(
            @PathVariable(value = "courseId")UUID courseId,
            @PathVariable(value = "moduleId")UUID moduleId) {
        Optional<Module> moduleOptional = moduleService.findModuleIntoCourse(courseId, moduleId);
        if(!moduleOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module Not Found for this course!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(moduleOptional.get());
    }
}
