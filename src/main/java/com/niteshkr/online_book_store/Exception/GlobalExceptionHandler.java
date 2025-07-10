package com.niteshkr.online_book_store.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> HandleProductNotFoundException(ProductNotFoundException ex){
        Map<String,Object> errorDetails=new HashMap<>();
        errorDetails.put("Details",ex.getMessage());
        errorDetails.put("Status Code", HttpStatus.NOT_FOUND);
        errorDetails.put("timeStamp", LocalDateTime.now());

        return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleIllegalArgumentException(Exception ex){
        Map<String,Object> errorDetails=new HashMap<>();
        errorDetails.put("Details",ex.getMessage());
        errorDetails.put("Status Code", HttpStatus.BAD_REQUEST.value());
        errorDetails.put("timeStamp", LocalDateTime.now());

        return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);

    }


}
