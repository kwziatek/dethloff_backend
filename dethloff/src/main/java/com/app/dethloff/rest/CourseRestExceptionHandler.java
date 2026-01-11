package com.app.dethloff.rest;

import com.app.dethloff.error.ErrorResponse;
import com.app.dethloff.error.CourseNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CourseRestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(CourseNotFoundException exc) {

        ErrorResponse courseErrorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(courseErrorResponse, HttpStatus.NOT_FOUND);
    }
}
