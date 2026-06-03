package com.devcaleb.ead.course.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ModuleDTO {

    @NotBlank
    private String title;
    @NotBlank
    private String description;
}
