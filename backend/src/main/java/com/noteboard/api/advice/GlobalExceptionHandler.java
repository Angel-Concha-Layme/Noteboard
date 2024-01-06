package com.noteboard.api.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.noteboard.api.exception.CategoryNotFoundException;
import com.noteboard.api.exception.NoteNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
        
    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<Object> handleNoteNotFoundException(NoteNotFoundException exception, WebRequest request) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Object> handleCategoryNotFoundException(CategoryNotFoundException exception, WebRequest request) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
    

}


