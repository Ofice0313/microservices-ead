package com.devcaleb.ead.course.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CourseUserDTO {

    private UUID courseId;
    private UUID userId;
}
