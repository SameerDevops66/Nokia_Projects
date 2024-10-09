package com.nokia.reactivejokess.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nokia.reactivejokess.domain.JokesResponseDto;
import com.nokia.reactivejokess.error.JokesException;
import com.nokia.reactivejokess.service.JokesService;

import reactor.core.publisher.Mono;

@RestController
public class JokesController {
	
	private final JokesService jokesService;
	
	 public JokesController(JokesService jokesService) {
	        this.jokesService = jokesService;
	    }
	
	 @GetMapping("/jokes")
	 public Mono<ResponseEntity<List<List<JokesResponseDto>>>> getJokes(@RequestParam Integer count) {
	     return jokesService.getJokes(count)
	         .map(jokeResponseBatches -> ResponseEntity.ok(jokeResponseBatches))
	         .onErrorResume(JokesException.class, e -> {
	             System.err.println("Error occurred: " + e.getMessage());
	             return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
	         });
	 }
}
