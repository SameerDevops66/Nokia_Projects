package com.nokia.reactivejokes;

import org.mockito.Mock;
import static org.mockito.Mockito.when;

import reactor.core.publisher.Mono;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.mockito.InjectMocks;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.nokia.reactivejokes.controller.JokesController;
import com.nokia.reactivejokes.domain.JokesResponseDto;
import com.nokia.reactivejokes.service.JokesService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import static org.springframework.test.web.reactive.server.WebTestClient.bindToController;

import java.util.Arrays;
import java.util.List;


public class JokesControllerTest {
	
	@Mock
 	private JokesService jokesService;
	
	@InjectMocks
	private JokesController jokesController;

	private WebTestClient webTestClient;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		webTestClient = bindToController(jokesController).build();
		
	}
	
	@Test
    public void test_getJokesValidate() {
    	
    	List<JokesResponseDto> jokesResponse = Arrays.asList(
    			new JokesResponseDto("1", "Setup 1", "Punchline 1"), 
    			new JokesResponseDto("2", "Setup 2", "Punchline 2"));
    	
    	Mono<List<List<JokesResponseDto>>> jokesResponseDto = Mono.just(Arrays.asList(jokesResponse));
    	
    	when (jokesService.getJokes(2)).thenReturn(jokesResponseDto);
    	
    	webTestClient.get().uri("/jokes?count=2")
    	.accept(MediaType.APPLICATION_JSON)
    	.exchange()
    	.expectStatus().isOk()
    	.expectBody()
    	.jsonPath("$[0][0].question").isEqualTo("Setup 1")
    	.jsonPath("$[0][0].answer").isEqualTo("Punchline 1");
    }

    @Test
    public void testGetJokes_InvalidCount() {
        webTestClient.get().uri("/jokes?count=")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    public void testGetJokes_MissingParameter() {
        webTestClient.get().uri("/jokes")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    public void testGetJokes_NoContent() {
        when(jokesService.getJokes(0)).thenReturn(Mono.just(Arrays.asList()));

        webTestClient.get().uri("/jokes?count=2")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is5xxServerError();
    }

    @Test
    public void testGetJokes_ServiceError() {
        when(jokesService.getJokes(2)).thenThrow(new RuntimeException("Service error"));

        webTestClient.get().uri("/jokes?count=2")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is5xxServerError();
    }

}
