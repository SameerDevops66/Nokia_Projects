package com.nokia.reactivejokes.infrastructure;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.nokia.reactivejokes.domain.JokesRequestDto;
import com.nokia.reactivejokes.error.JokesException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class JokesPublicApiClient {
	
	private final WebClient webClient;

    public JokesPublicApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://official-joke-api.appspot.com").build();
    }
    
    public Flux<JokesRequestDto> fetchBatch(int batchSize) {
        return Flux.range(1, batchSize)
            .flatMap(i -> webClient.get()
                .uri("/random_joke")
                .retrieve()
                .onStatus(HttpStatus::isError, response -> {
                	 if (response.statusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                         return Mono.error(new JokesException("Your IP has exceeded the 100 request limit per 15 minute(s). Try again in 15 minute(s)."));
                     } else {
                         return Mono.error(new JokesException("Jokes API returned error status code: " + response.statusCode().value()));
                     }
                 })
                .bodyToMono(JokesRequestDto.class)
                .onErrorResume(WebClientResponseException.class, ex -> 
                    Mono.error(new JokesException("Error occurred while calling Jokes API: " + ex.getMessage()))
                )
            );
    }
}
