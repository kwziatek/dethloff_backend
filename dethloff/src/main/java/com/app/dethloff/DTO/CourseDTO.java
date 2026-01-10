package com.app.dethloff.DTO;

import com.app.dethloff.model.CourseLevel;
import lombok.Builder;

@Builder
public record CourseDTO (
    String id,
    CourseLevel level,
    String description
) {}
