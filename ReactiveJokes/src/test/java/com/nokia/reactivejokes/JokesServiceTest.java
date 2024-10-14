package com.nokia.reactivejokes;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.nokia.reactivejokes.domain.JokesRepository;
import com.nokia.reactivejokes.domain.JokesRequestDto;
import com.nokia.reactivejokes.domain.JokesResponseDto;
import com.nokia.reactivejokes.error.JokesException;
import com.nokia.reactivejokes.infrastructure.JokesPublicApiClient;
import com.nokia.reactivejokes.service.JokesService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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
            .expectComplete();
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
    
    @Test
    public void testGetJokesThrowsJokesException() {
        when(jokeApiClient.fetchBatch(1)).thenReturn(Flux.error(new JokesException("API error")));

        StepVerifier.create(jokesService.getJokes(1))
            .expectErrorMatches(throwable -> throwable instanceof JokesException &&
                    throwable.getMessage().equals("API error"))
            .verify();
    }
    
    
}
