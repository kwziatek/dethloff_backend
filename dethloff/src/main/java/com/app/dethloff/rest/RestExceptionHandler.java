package com.app.dethloff.rest;

import com.app.dethloff.rest.error.CourseNotFoundException;
import com.app.dethloff.rest.error.ErrorResponse;
import com.app.dethloff.rest.error.StudentNotFoundException;
import com.app.dethloff.rest.error.TeacherNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({
            StudentNotFoundException.class,
            CourseNotFoundException.class,
            TeacherNotFoundException.class
    })
    ResponseEntity<ErrorResponse> handleObjectNotFoundException(RuntimeException exc) {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
