package com.nokia.reactivejokes;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.nokia.reactivejokes.domain.JokesRepository;
import com.nokia.reactivejokes.domain.JokesRequestDto;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class JokesRepositoryTests {
	
	    @Mock
	    private JokesRepository jokesRepository;

	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    public void testSaveJoke() {
	    	
	        JokesRequestDto joke = new JokesRequestDto();
	        joke.setId(55);
	        joke.setQuestion("Why did the chicken");
	        joke.setAnswer("To get to the other side");

	        when(jokesRepository.save(joke)).thenReturn(Mono.just(joke));

	        Mono<JokesRequestDto> savedJoke = jokesRepository.save(joke);

	        StepVerifier.create(savedJoke)
	            .expectNextMatches(j -> j.getId() == 55)
	            .verifyComplete();
	    }
}
