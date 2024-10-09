package com.nokia.reactivejokes.error;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(JokesException.class)
	 public ResponseEntity<Map<String, String>> handleJokesException(JokesException ex) {
	        Map<String, String> errorResponse = new HashMap<>();
	        
	        if (ex.getMessage().contains("Database")) {
	            errorResponse.put("error", "Database Error");
	            return ResponseEntity
	                    .status(HttpStatus.SERVICE_UNAVAILABLE)
	                    .body(errorResponse);
	        }
	        
	        if (ex.getMessage().contains("Too Many Requests")) {
	        	
	            errorResponse.put("error", "To Many Requests");
	            errorResponse.put("message", ex.getMessage());
	            return ResponseEntity
	                    .status(HttpStatus.TOO_MANY_REQUESTS)
	                    .body(errorResponse);
	        }
	        
	        else {
	            errorResponse.put("error", "Bad Request");
	            errorResponse.put("message", ex.getMessage());
	            return ResponseEntity
	                    .status(HttpStatus.BAD_REQUEST)
	                    .body(errorResponse);
	        }
	        
	        
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
	        Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("error", "Internal Server Error");
	        errorResponse.put("message", ex.getMessage());
	        return ResponseEntity
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(errorResponse);
	    }
	    

}
