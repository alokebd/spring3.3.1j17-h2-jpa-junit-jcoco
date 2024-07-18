package com.rbc.uscm.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ApplicationExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(value= MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
        log.error(HttpStatus.METHOD_NOT_ALLOWED + ex.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(),
                ex.getMessage()));
    }
   
    @ExceptionHandler(value=ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
       log.error(HttpStatus.NOT_FOUND + ex.getMessage());
       return ResponseEntity.status(HttpStatus.NOT_FOUND)
    		   .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                ex.getMessage()));
    }
            
    @ExceptionHandler(value=ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex){
        log.error(HttpStatus.CONFLICT + ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
        		.body(new ErrorResponse(HttpStatus.CONFLICT.value(),
                ex.getMessage()));
    }

}
