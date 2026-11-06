package com.devcaleb.ead.authUser.dto;

import com.devcaleb.ead.authUser.enums.CourseLevel;
import com.devcaleb.ead.authUser.enums.CourseStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class CourseDTO {

    private UUID courseId;
    private String name;
    private String description;
    private String imageUrl;
    private CourseStatus courseStatus;
    private CourseLevel courseLevel;
    private UUID userInstructor;
}
