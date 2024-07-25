package com.todoserver.todo.exception.advice;

import com.todoserver.todo.exception.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.todoserver.todo.controller")
public class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalArgumentHandler(IllegalArgumentException e) {
        log.error("exception ",e);
        return new ErrorResult("400 BAD REQUEST", e.getMessage());
    }
}
