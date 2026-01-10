package com.app.dethloff.error;

import lombok.Data;

@Data
public class CourseErrorResponse {
    private String message;
    private int status;
    private long timeStamp;
}
