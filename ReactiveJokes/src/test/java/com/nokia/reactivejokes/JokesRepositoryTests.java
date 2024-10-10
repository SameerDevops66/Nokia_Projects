package com.nokia.reactivejokes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;

import com.nokia.reactivejokes.domain.JokesRepository;
import com.nokia.reactivejokes.domain.JokesRequestDto;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataR2dbcTest
public class JokesRepositoryTests {
	
	 @Autowired
	  private JokesRepository jokesRepository;

	  @Test
	  public void testSaveJoke() {
		    JokesRequestDto joke = new JokesRequestDto();
		    joke.setId(99);
		    joke.setQuestion("Why did the chicken");
		    joke.setAnswer("To get to the other side");

		    Mono<JokesRequestDto> savedJoke = jokesRepository.save(joke);

		    StepVerifier.create(savedJoke)
		        .expectNextMatches(j -> j.getId() == 99)
		        .verifyComplete();
		}

}
