package com.nokia.reactivejokess.service;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.nokia.reactivejokess.domain.JokesRepository;
import com.nokia.reactivejokess.domain.JokesRequestDto;
import com.nokia.reactivejokess.domain.JokesResponseDto;
import com.nokia.reactivejokess.error.JokesException;
import com.nokia.reactivejokess.infrastructure.JokesPublicApiClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class JokesService {
	
	private final JokesPublicApiClient jokeApiClient;
    private final JokesRepository jokesRepository;

    public JokesService(JokesPublicApiClient jokeApiClient, JokesRepository jokesRepository) {
        this.jokeApiClient = jokeApiClient;
        this.jokesRepository = jokesRepository;
    }
    
    public Mono<List<List<JokesResponseDto>>> getJokes(int count) {
        int batchSize = 10;
        return getJokesInBatches(count, batchSize)
        		.onBackpressureBuffer(batchSize, joke -> {
                })
            .buffer(batchSize)
            .flatMap(jokesBatch -> {
            	 if (jokesBatch == null || jokesBatch.isEmpty()) {
            	        return Mono.error(new JokesException("No jokes to save"));
            	    }
                return jokesRepository.saveAll(jokesBatch)
                    .doOnError(e -> handleDatabaseError(e))
                    .thenMany(Flux.just(jokesBatch));
            })
            .map(this::transformToResponseDto)
            .delayElements(Duration.ofMillis(100)) 
            .collectList()
            .flatMap(jokesResponseBatches -> {
                  return Mono.just(jokesResponseBatches);
            });
    }


    public Flux<JokesRequestDto> getJokesInBatches(int totalCount, int batchSize) {
    	
        int fullBatches = totalCount / batchSize;
        int remainder = totalCount % batchSize;

      
        Flux<JokesRequestDto> fullBatchFlux = Flux.range(0, fullBatches)
            .flatMap(batch -> jokeApiClient.fetchBatch(batchSize),1);

       
        Flux<JokesRequestDto> remainderBatchFlux = remainder > 0 ? jokeApiClient.fetchBatch(remainder) : Flux.empty();

      
        return Flux.concat(fullBatchFlux, remainderBatchFlux)
            .distinct(JokesRequestDto::getId)
            .take(totalCount)
            .onBackpressureBuffer(50);
    }

   
    private void handleDatabaseError(Throwable e) {
        if (e instanceof DataAccessException) {
            throw new JokesException("Database is down, please try again later.");
        } else {
            throw new JokesException("Invalid joke request.");
        }
    }

    
    public List<JokesResponseDto> transformToResponseDto(List<JokesRequestDto> jokesBatch) {
    	if (jokesBatch == null || jokesBatch.isEmpty()) {
            throw new JokesException("No valid jokes found for transformation.");
    	}
        return jokesBatch.stream()
            .map(joke -> new JokesResponseDto(
                UUID.randomUUID().toString(),
                joke.getQuestion(),
                joke.getAnswer()))
            .collect(Collectors.toList());
    }	

}
