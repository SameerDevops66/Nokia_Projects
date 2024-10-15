package com.nokia.reactivejokes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nokia.reactivejokes.domain.JokesResponseDto;
import com.nokia.reactivejokes.error.JokesException;
import com.nokia.reactivejokes.service.JokesService;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//
//import io.swagger.v3.oas.annotations.Operation;
import reactor.core.publisher.Mono;

@RestController
public class JokesController {
	
	private final JokesService jokesService;
	
	 public JokesController(JokesService jokesService) {
	        this.jokesService = jokesService;
	    }
	
//	 @Operation(summary = "Returns a list of jokes", responses = {
//		        @ApiResponse(description = "A JSON array of jokes", responseCode = "200", 
//		            content = @Content(mediaType = "application/json",
//		                schema = @Schema(implementation = JokesResponseDto.class)))})
	 @GetMapping("/jokes")
	 public Mono<ResponseEntity<List<List<JokesResponseDto>>>> getJokes(@RequestParam Integer count) {
	     return jokesService.getJokes(count)
	         .map(jokeResponseBatches -> ResponseEntity.ok(jokeResponseBatches))
	         .onErrorResume(JokesException.class, e -> {
	             return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
	         });
	 }
}
