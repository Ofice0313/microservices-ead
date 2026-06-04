package com.devcaleb.ead.course.controllers;

import com.devcaleb.ead.course.dto.LessonDTO;
import com.devcaleb.ead.course.entities.Lesson;
import com.devcaleb.ead.course.entities.Module;
import com.devcaleb.ead.course.services.LessonService;
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
@RequestMapping(value = "/api/lessons")
@CrossOrigin(origins = "*", maxAge = 36000)
public class LessonController {

    @Autowired
    LessonService lessonService;

    @Autowired
    ModuleService moduleService;

    @PostMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<Object> saveLesson(
            @PathVariable(value = "moduleId") UUID moduleId,
            @RequestBody @Valid LessonDTO lessonDTO) {
        Optional<Module> moduleOptional = moduleService.findById(moduleId);
        if(!moduleOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module Not Found!");
        }
        var lesson = new Lesson();
        BeanUtils.copyProperties(lessonDTO, lesson);
        lesson.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        lesson.setModule(moduleOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.save(lesson));
    }

    @DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "moduleId")UUID moduleId,
                                         @PathVariable(value = "lessonId")UUID lessonId
                                         ) {
        Optional<Lesson> lessonOptional = lessonService.findLessonIntoModule(moduleId, lessonId);
        if(!lessonOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson Not Found for this module!");
        }
        lessonService.delete(lessonOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Lesson deleted successfully!");
    }

    @PutMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> update(
            @PathVariable(value = "moduleId")UUID moduleId,
            @PathVariable(value = "lessonId")UUID lessonId,
            @RequestBody @Valid LessonDTO lessonDTO) {
        Optional<Lesson> lessonOptional = lessonService.findLessonIntoModule(moduleId, lessonId);
        if(!lessonOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson Not Found for this module!");
        }
        var lesson = lessonOptional.get();

        lesson.setTitle(lessonDTO.getTitle());
        lesson.setDescription(lessonDTO.getDescription());
        lesson.setVideoUrl(lessonDTO.getVideoUrl());
        return ResponseEntity.status(HttpStatus.OK).body(lessonService.save(lesson));
    }

    @GetMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<Page<Lesson>> getAllLessons(
            @PathVariable(value = "moduleId")UUID moduleId,
            SpecificationTemplate.LessonSpec spec,
            @PageableDefault(page = 0, size = 10, sort = "lessonId", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(lessonService.findAllByModule(SpecificationTemplate.lessonModuleId(moduleId).and(spec), pageable));
    }

    @GetMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> getOneLesson(
            @PathVariable(value = "moduleId")UUID moduleId,
            @PathVariable(value = "lessonId")UUID lessonId) {
        Optional<Lesson> lessonOptional = lessonService.findLessonIntoModule(moduleId, lessonId);
        if(!lessonOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson Not Found for this module!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(lessonOptional.get());
    }
}
