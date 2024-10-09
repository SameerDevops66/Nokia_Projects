package com.nokia.reactivejokess;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.nokia.reactivejokess.domain.JokesRepository;
import com.nokia.reactivejokess.domain.JokesRequestDto;
import com.nokia.reactivejokess.domain.JokesResponseDto;
import com.nokia.reactivejokess.error.JokesException;
import com.nokia.reactivejokess.infrastructure.JokesPublicApiClient;
import com.nokia.reactivejokess.service.JokesService;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
public class JokesServiceTest {
	
	@Mock
    private JokesPublicApiClient jokeApiClient;

    @Mock
    private JokesRepository jokesRepository;

    @InjectMocks
	private JokesService jokesService;


    @Test
    public void testGetJokesEmptyList() {
        when(jokeApiClient.fetchBatch(0)).thenReturn(Flux.empty());

        StepVerifier.create(jokesService.getJokes(0))
            .expectErrorMatches(throwable -> 
                throwable instanceof JokesException && 
                throwable.getMessage().equals("No jokes found")
            )
            .verify();
    }
    
    @Test
    public void testGetJokesApiError() {
        when(jokeApiClient.fetchBatch(10)).thenReturn(Flux.error(new RuntimeException("API error")));

        StepVerifier.create(jokesService.getJokes(10))
            .expectErrorMatches(throwable -> throwable instanceof RuntimeException && throwable.getMessage().equals("API error"))
            .verify();
    }
    
    @Test
    public void testTransformToResponseDto() {
        List<JokesRequestDto> mockJokesBatch = List.of(
            new JokesRequestDto(1, "Why did the chicken cross the road?", "To get to the other side."),
            new JokesRequestDto(2, "What do you call a fish with no eyes?", "A fsh.")
        );

        List<JokesResponseDto> expectedResponseBatch = mockJokesBatch.stream()
            .map(joke -> new JokesResponseDto(UUID.randomUUID().toString(), joke.getQuestion(), joke.getAnswer()))
            .collect(Collectors.toList());

        List<JokesResponseDto> actualResponse = jokesService.transformToResponseDto(mockJokesBatch);

        assertEquals(expectedResponseBatch.size(), actualResponse.size());
    }
    

}
