package com.nokia.reactivejokes.infrastructure;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.nokia.reactivejokes.domain.JokesRequestDto;
import com.nokia.reactivejokes.error.JokesException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class JokesPublicApiClient {
	
	private final WebClient webClient;

    public JokesPublicApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://official-joke-api.appspot.com").build();
    }
    
    public Flux<JokesRequestDto> fetchBatch(int batchSize) {
        return Flux.range(1, batchSize)
        		.log()
                .flatMap(i -> webClient.get()
                        .uri("/random_joke")
                        .retrieve()
                        .bodyToMono(JokesRequestDto.class));
        }

}
