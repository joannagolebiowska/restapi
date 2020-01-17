package com.merapar.xmlanalyzer.xmlanalyzer.exception;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class IOExceptionHandler {


    @ExceptionHandler(IOException.class)
    public String globalExceptionHandle(Exception ex){
       return ex.getMessage();
    }
}
