package com.appointment.utility;

import java.time.LocalDateTime;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.appointment.exception.HmsException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionControllerAdvice {
	
	@Autowired
	Environment env;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorInfo> exceptionHandler(Exception e){
		ErrorInfo error = new ErrorInfo("Some error ocured",HttpStatus.INTERNAL_SERVER_ERROR.value(),LocalDateTime.now());
		return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(HmsException.class)
	public ResponseEntity<ErrorInfo> HmsExceptionHandler(HmsException e){
		ErrorInfo error = new ErrorInfo(env.getProperty(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR.value(),LocalDateTime.now());
		return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler({MethodArgumentNotValidException.class,ConstraintViolationException.class})
	public ResponseEntity<ErrorInfo> handleValidatonException(Exception e){
		String errorMsg;
		if(e instanceof MethodArgumentNotValidException mnve) {
			errorMsg = mnve.getBindingResult()
					                     .getAllErrors()
					                     .stream()
					                     .map(ObjectError::getDefaultMessage)
					                     .collect(Collectors.joining(", "));
		}else {
			ConstraintViolationException violationException = (ConstraintViolationException) e;
			errorMsg = violationException
					              .getConstraintViolations()
					              .stream()
					              .map(ConstraintViolation::getMessage)
					              .collect(Collectors.joining(", "));
		}
		ErrorInfo errorInfo = new ErrorInfo(errorMsg,HttpStatus.INTERNAL_SERVER_ERROR.value(),LocalDateTime.now());
		return new ResponseEntity<>(errorInfo,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
