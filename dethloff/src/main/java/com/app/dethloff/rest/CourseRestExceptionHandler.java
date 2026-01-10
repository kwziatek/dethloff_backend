package com.app.dethloff.rest;

import com.app.dethloff.error.CourseErrorResponse;
import com.app.dethloff.error.CourseNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CourseRestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CourseErrorResponse> handleException(CourseNotFoundException exc) {

        CourseErrorResponse courseErrorResponse = new CourseErrorResponse();

        courseErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        courseErrorResponse.setMessage(exc.getMessage());
        courseErrorResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(courseErrorResponse, HttpStatus.NOT_FOUND);
    }
}
