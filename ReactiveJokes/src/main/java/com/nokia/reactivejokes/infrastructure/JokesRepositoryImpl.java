package com.nokia.reactivejokes.infrastructure;

import java.util.List;
import java.util.UUID;

import org.reactivestreams.Publisher;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.nokia.reactivejokes.domain.JokesRepository;
import com.nokia.reactivejokes.domain.JokesRequestDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class JokesRepositoryImpl implements JokesRepository {
	
private final ReactiveCrudRepository<JokesRequestDto, UUID> jokeCrudRepository;
	
	public JokesRepositoryImpl(ReactiveCrudRepository<JokesRequestDto, UUID> jokeCrudRepository) {
        this.jokeCrudRepository = jokeCrudRepository;
    }
	
	
	 	@Override
	    public Flux<JokesRequestDto> saveAll(List<JokesRequestDto> jokes) {
	        return jokeCrudRepository.saveAll(jokes);
	    }


		
}
