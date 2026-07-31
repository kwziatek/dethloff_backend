package com.app.dethloff.exceptions;

import com.app.dethloff.exceptions.model.*;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;
import java.time.format.DateTimeParseException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            StudentNotFoundException.class,
            CourseNotFoundException.class,
            TeacherNotFoundException.class,
            UserNotFoundException.class
    })
    ResponseEntity<ErrorResponse> handleObjectNotFoundException(RuntimeException exc) {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            InvalidPeselException.class,
            DateTimeParseException.class,
            InvalidPasswordException.class,
            NullPointerException.class,
            MethodArgumentNotValidException.class,
            UsernameTakenException.class

    })
    ResponseEntity<ErrorResponse> handleBadRequestException(Exception exc) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(), System.currentTimeMillis());

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
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), exc.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
