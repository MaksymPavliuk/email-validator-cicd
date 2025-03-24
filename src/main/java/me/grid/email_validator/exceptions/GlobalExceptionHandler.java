package me.grid.email_validator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(Exception ex) {
        System.out.println("Exception caught: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleMissingBodyException(Exception ex) {
        System.out.println("Exception caught: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + "Request body is missing. Try {\"emails\":[...]}");
    }

}