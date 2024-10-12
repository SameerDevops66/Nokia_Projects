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
