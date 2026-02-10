package com.example.demo.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	
	public String resourceNotFound(RuntimeException e)
	{	
		return e.getMessage();
	}
}
