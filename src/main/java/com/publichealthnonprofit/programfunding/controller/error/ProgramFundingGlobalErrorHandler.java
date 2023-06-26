package com.publichealthnonprofit.programfunding.controller.error;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ProgramFundingGlobalErrorHandler {
    
    private enum LogStatus {
        MESSAGE_ONLY, 
        FULL_STACK_TRACE
    }

    @Data
    private class ExceptionMessage {
        private String message;
        private int statusCode;
        private String statusReason;
        private String timestamp;
        private String uri;
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionMessage handleException(Exception exception, WebRequest webRequest) {
        return buildExceptionMessage(exception, HttpStatus.INTERNAL_SERVER_ERROR, webRequest, LogStatus.FULL_STACK_TRACE);
    }
    
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionMessage handleNotFoundException(NoSuchElementException exception, WebRequest webRequest) {
        return buildExceptionMessage(exception, HttpStatus.NOT_FOUND, webRequest, LogStatus.MESSAGE_ONLY);
    }
    
    @ExceptionHandler(UnsupportedOperationException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ExceptionMessage handleUnsupportedOperationException(UnsupportedOperationException exception, WebRequest webRequest) {
        return buildExceptionMessage(exception, HttpStatus.METHOD_NOT_ALLOWED, webRequest, LogStatus.MESSAGE_ONLY);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage handleIllegalArgumentException(IllegalArgumentException exception, WebRequest webRequest) {
        return buildExceptionMessage(exception, HttpStatus.BAD_REQUEST, webRequest, LogStatus.MESSAGE_ONLY);
    }

    private ExceptionMessage buildExceptionMessage(Exception exception, HttpStatus httpStatus, WebRequest webRequest, LogStatus logStatus) {
		String message = exception.toString();
		String statusReason = httpStatus.getReasonPhrase();
		int statusCode = httpStatus.value();
		String timestamp = ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME);
		String uri = null;
		if (webRequest instanceof ServletWebRequest servletWebRequest) {
			uri = servletWebRequest.getRequest().getRequestURI();
		}
		if (logStatus == LogStatus.MESSAGE_ONLY) {
			log.error("Exception: {}", exception.toString());
		} else {
			log.error("Exception: ", exception);
		}
		ExceptionMessage exceptionMessage = new ExceptionMessage();
		exceptionMessage.setMessage(message);
		exceptionMessage.setStatusCode(statusCode);
		exceptionMessage.setStatusReason(statusReason);
		exceptionMessage.setTimestamp(timestamp);
		exceptionMessage.setUri(uri);
		return exceptionMessage;
	}
}
