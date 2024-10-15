package com.nokia.reactivejokes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.nokia.reactivejokes.domain.JokesRequestDto;
import com.nokia.reactivejokes.error.JokesException;
import com.nokia.reactivejokes.infrastructure.JokesPublicApiClient;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@SpringBootTest
public class JokesPublicApiClientTests {

    @Mock
    private JokesPublicApiClient jokesPublicApiClient;  

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    
    @Test
    public void fetchBatch_emptyBatch() {
        when(jokesPublicApiClient.fetchBatch(1)).thenReturn(Flux.empty());

        Flux<JokesRequestDto> result = jokesPublicApiClient.fetchBatch(1);

        StepVerifier.create(result)
            .expectNextCount(0)
            .verifyComplete();
    }
    
    @Test
    public void fetchBatch_multipleJokes() {
        JokesRequestDto joke1 = new JokesRequestDto(1, "Why did the chicken cross the road?", "To get to the other side");
        JokesRequestDto joke2 = new JokesRequestDto(2, "Why don't skeletons fight each other?", "They don't have the guts.");

        when(jokesPublicApiClient.fetchBatch(2)).thenReturn(Flux.just(joke1, joke2));

        Flux<JokesRequestDto> result = jokesPublicApiClient.fetchBatch(2);

        StepVerifier.create(result)
            .expectNext(joke1)
            .expectNext(joke2)
            .verifyComplete();
    }
    
    @Test
    public void fetchBatch_internalServerError() {
        when(jokesPublicApiClient.fetchBatch(1)).thenReturn(Flux.error(new JokesException("500 Internal Server Error")));

        Flux<JokesRequestDto> result = jokesPublicApiClient.fetchBatch(1);

        StepVerifier.create(result)
            .expectErrorMatches(throwable -> throwable instanceof JokesException &&
                    throwable.getMessage().contains("500 Internal Server Error"))
            .verify();
    }
    
    @Test
    public void fetchBatch_partialBatchWithError() {
        JokesRequestDto joke1 = new JokesRequestDto(1, "Why did the chicken cross the road?", "To get to the other side");

        when(jokesPublicApiClient.fetchBatch(2)).thenReturn(Flux.concat(
            Flux.just(joke1),
            Flux.error(new JokesException("500 Internal Server Error"))
        ));

        Flux<JokesRequestDto> result = jokesPublicApiClient.fetchBatch(2);

        StepVerifier.create(result)
            .expectNext(joke1)
            .expectErrorMatches(throwable -> throwable instanceof JokesException &&
                    throwable.getMessage().contains("500 Internal Server Error"))
            .verify();
    }
    
    @Test
    public void fetchBatch_invalidJokeId() {
        when(jokesPublicApiClient.fetchBatch(-1)).thenReturn(Flux.error(new IllegalArgumentException("Invalid joke ID")));

        Flux<JokesRequestDto> result = jokesPublicApiClient.fetchBatch(-1);

        StepVerifier.create(result)
            .expectErrorMatches(throwable -> throwable instanceof IllegalArgumentException &&
                    throwable.getMessage().contains("Invalid joke ID"))
            .verify();
    }

    @Test
    public void fetchBatch_success() {
    	
        JokesRequestDto mockJoke = new JokesRequestDto();
        
        mockJoke.setId(1);
        mockJoke.setQuestion("Why did the chicken cross the road?");
        mockJoke.setAnswer("To get to the other side");

        when(jokesPublicApiClient.fetchBatch(1)).thenReturn(Flux.just(mockJoke));

        Flux<JokesRequestDto> result = jokesPublicApiClient.fetchBatch(1);

        StepVerifier.create(result)
            .expectNextMatches(joke -> {

            	String actualQuestion = joke.getQuestion().trim().replace(",", "").replace(" ", "");
                String expectedQuestion = "Why did the chicken cross the road?".trim().replace(",", "").replace(" ", "");
                
                String actualAnswer = joke.getAnswer().trim().replace(",", "").replace(" ", "");
                String expectedAnswer = "To get to the other side".trim().replace(",", "").replace(" ", "");

                return joke.getId() == 1 &&
                       actualQuestion.equalsIgnoreCase(expectedQuestion) &&
                       actualAnswer.equalsIgnoreCase(expectedAnswer);
            })
            .verifyComplete();
    }

    @Test
    public void fetchBatch_tooManyRequests() {
    	
       when(jokesPublicApiClient.fetchBatch(100)).thenReturn(Flux.error(new JokesException("429 To Many Request, Try after 15 Mints")));

        Flux<JokesRequestDto> result = jokesPublicApiClient.fetchBatch(100);

        StepVerifier.create(result)
            .expectErrorMatches(throwable -> throwable instanceof JokesException &&
                    throwable.getMessage().contains("429 To Many Request, Try after 15 Mints"))
            .verify();
    }
    
    
}
