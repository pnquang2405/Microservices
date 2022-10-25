package com.test.gatewayservice.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExeptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(value= {AuthorException.class})
	public ResponseEntity<?> resourceNotFoundHandling(AuthorException exception) {
		ErrorDetails errorDetails = new ErrorDetails(exception.getErrorCode(), exception.getMessage());
		System.out.println(exception.getErrorMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalExceptionHandling
							(Exception exception, WebRequest request) {
		ErrorDetails errorDetails =  new ErrorDetails(401, exception.getMessage());
		
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
