package com.nokia.reactivejokes.domain;

import java.util.List;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public interface JokesRepository extends ReactiveCrudRepository<JokesRequestDto, Integer> {
	
	Flux<JokesRequestDto> saveAll(List<JokesRequestDto> jokes);	

}
