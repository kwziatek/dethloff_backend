package com.app.dethloff.DTO.mappers;


import com.app.dethloff.DTO.CourseDTO;
import com.app.dethloff.model.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseMapper {

    public CourseMapper() {

    }

    public static CourseDTO toDTO(Course course) {
        return CourseDTO.builder()
                .id(course.getId())
                .level(course.getLevel())
                .description(course.getDescription())
                .build();
    }

    public static Course toCourse(CourseDTO courseDTO) {
        return Course.builder()
                .id(courseDTO.id())
                .level(courseDTO.level())
                .description(courseDTO.description())
                .build();
    }

    public static List<CourseDTO> toDTO(List<Course> courseList) {
        List<CourseDTO> result = new ArrayList<>();
        for(Course course: courseList) {
            result.add(toDTO(course));
        }
        return result;
    }

    public static List<Course> toCourse(List<CourseDTO> courseDTOList) {
        List<Course> result = new ArrayList<>();
        for(CourseDTO courseDTO: courseDTOList) {
            result.add(toCourse(courseDTO));
        }
        return result;
    }
}
