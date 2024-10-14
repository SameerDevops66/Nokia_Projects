package com.nokia.reactivejokes;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.nokia.reactivejokes.domain.JokesRepository;
import com.nokia.reactivejokes.domain.JokesRequestDto;

import reactor.core.publisher.Flux;
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
	    
	    @Test
	    public void testSaveJokeWithNullValues() {
	        JokesRequestDto joke = new JokesRequestDto();
	        joke.setId(0); // No ID
	        joke.setQuestion(null); // Null question
	        joke.setAnswer(null);   // Null answer

	        when(jokesRepository.save(joke)).thenReturn(Mono.just(joke));

	        Mono<JokesRequestDto> savedJoke = jokesRepository.save(joke);

	        StepVerifier.create(savedJoke)
	            .expectNextMatches(j -> j.getId() == 0 && j.getQuestion() == null && j.getAnswer() == null)
	            .verifyComplete();
	    }
	    
	    @Test
	    public void testFindJokeById() {
	        int jokeId = 1;
	        JokesRequestDto joke = new JokesRequestDto();
	        joke.setId(jokeId);
	        joke.setQuestion("Why did the chicken");
	        joke.setAnswer("To get to the other side");

	        when(jokesRepository.findById(jokeId)).thenReturn(Mono.just(joke));

	        Mono<JokesRequestDto> foundJoke = jokesRepository.findById(jokeId);

	        StepVerifier.create(foundJoke)
	            .expectNextMatches(j -> j.getId() == jokeId)
	            .verifyComplete();

	        // Verify the interaction with the repository
	        verify(jokesRepository, times(1)).findById(jokeId);
	    }
	    
	    @Test
	    public void testFindJokeById_NotFound() {
	        int jokeId = 999; // Non-existent ID

	        when(jokesRepository.findById(jokeId)).thenReturn(Mono.empty());

	        Mono<JokesRequestDto> foundJoke = jokesRepository.findById(jokeId);

	        StepVerifier.create(foundJoke)
	            .expectNextCount(0) // No jokes should be found
	            .verifyComplete();
	    }
	    
	    @Test
	    public void testFindAllJokes() {
	        JokesRequestDto joke1 = new JokesRequestDto();
	        joke1.setId(1);
	        joke1.setQuestion("Why did the chicken");
	        joke1.setAnswer("To get to the other side");

	        JokesRequestDto joke2 = new JokesRequestDto();
	        joke2.setId(2);
	        joke2.setQuestion("What do you call a fake noodle?");
	        joke2.setAnswer("An Impasta!");

	        when(jokesRepository.findAll()).thenReturn(Flux.just(joke1, joke2));

	        Flux<JokesRequestDto> allJokes = jokesRepository.findAll();

	        StepVerifier.create(allJokes)
	            .expectNext(joke1)
	            .expectNext(joke2)
	            .verifyComplete();

	        // Verify the interaction with the repository
	        verify(jokesRepository, times(1)).findAll();
	    }
	    
	    
	    @Test
	    public void testDeleteJoke() {
	        int jokeId = 55;

	        when(jokesRepository.deleteById(jokeId)).thenReturn(Mono.empty());

	        Mono<Void> deletedJoke = jokesRepository.deleteById(jokeId);

	        StepVerifier.create(deletedJoke)
	            .verifyComplete();

	        // Verify the interaction with the repository
	        verify(jokesRepository, times(1)).deleteById(jokeId);
	    }
}
