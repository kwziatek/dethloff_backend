package com.app.dethloff.exceptions;

import com.app.dethloff.exceptions.model.*;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import java.time.format.DateTimeParseException;
import javax.naming.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({
    StudentNotFoundException.class,
    CourseNotFoundException.class,
    TeacherNotFoundException.class,
    UserNotFoundException.class,
    LessonNotFoundException.class
  })
  ResponseEntity<ErrorResponse> handleObjectNotFoundException(RuntimeException exc) {

    ErrorResponse errorResponse =
        new ErrorResponse(
            HttpStatus.NOT_FOUND.value(), exc.getMessage(), System.currentTimeMillis());

    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({
    InvalidPeselException.class,
    DateTimeParseException.class,
    InvalidPasswordException.class,
    NullPointerException.class,
    MethodArgumentNotValidException.class,
    UsernameTakenException.class,
    StudentAlreadyEnrolledException.class,
    StudentNotEnrolledException.class
  })
  ResponseEntity<ErrorResponse> handleBadRequestException(Exception exc) {
    ErrorResponse errorResponse =
        new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(), exc.getMessage(), System.currentTimeMillis());

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({
    InvalidLoginOrPasswordException.class,
    InvalidJWTException.class,
    SignatureException.class,
    JwtException.class,
    AuthorizationDeniedException.class,
    AuthenticationException.class
  })
  ResponseEntity<ErrorResponse> handleUnauthorizedException(RuntimeException exc) {
    ErrorResponse errorResponse =
        new ErrorResponse(
            HttpStatus.UNAUTHORIZED.value(), exc.getMessage(), System.currentTimeMillis());

    return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(Exception.class)
  ResponseEntity<ErrorResponse> handleGeneralException(Exception exc) {
    ErrorResponse errorResponse =
        new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(), exc.getMessage(), System.currentTimeMillis());

    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
