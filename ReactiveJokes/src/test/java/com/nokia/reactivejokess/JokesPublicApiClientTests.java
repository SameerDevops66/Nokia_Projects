package com.nokia.reactivejokess;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import okhttp3.mockwebserver.MockResponse;


import com.nokia.reactivejokess.domain.JokesRequestDto;
import com.nokia.reactivejokess.error.JokesException;
import com.nokia.reactivejokess.infrastructure.JokesPublicApiClient;

import okhttp3.mockwebserver.MockWebServer;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest
public class JokesPublicApiClientTests {
	
	private MockWebServer mockWebServer;
    private JokesPublicApiClient jokesPublicApiClient;

    @BeforeEach
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        WebClient.Builder webClientBuilder = WebClient.builder()
                .baseUrl(mockWebServer.url("/").toString());

        jokesPublicApiClient = new JokesPublicApiClient(webClientBuilder);
    }

    @AfterEach
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }


    @Test
    public void fetchBatch_success() {
        String jokeJson = """
            {
              "id": 1,
              "type": "general",
              "setup": "Why did the chicken cross the road?",
              "punchline": "To get to the other side."
            }
            """;

        mockWebServer.enqueue(new MockResponse()
                .setBody(jokeJson)
                .addHeader("Content-Type", "application/json"));

        Flux<JokesRequestDto> result = jokesPublicApiClient.fetchBatch(1);

        StepVerifier.create(result)
        .expectNextMatches(joke -> joke.getId() != null &&
                joke.getQuestion() != null &&
                joke.getAnswer() != null) 
        .expectNextCount(1)
        .verifyComplete();
    }

    
    
    @Test
    public void fetchBatch_tooManyRequests() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(429)
                .setBody("Too Many Requests"));

        Flux<JokesRequestDto> result = jokesPublicApiClient.fetchBatch(100);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof JokesException &&
                        throwable.getMessage().contains("429 To Many Request, Try after 15 Mints"))
                .verify();
    }


}
