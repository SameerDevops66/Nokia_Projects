package com.nokia.reactivejokess.infrastructure;

import java.util.List;
import java.util.UUID;

import org.reactivestreams.Publisher;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.nokia.reactivejokess.domain.JokesRepository;
import com.nokia.reactivejokess.domain.JokesRequestDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class JokesRepositoryImpl implements JokesRepository {
	
private final ReactiveCrudRepository<JokesRequestDto, UUID> jokeCrudRepository;
	
	public JokesRepositoryImpl(ReactiveCrudRepository<JokesRequestDto, UUID> jokeCrudRepository) {
        this.jokeCrudRepository = jokeCrudRepository;
    }
	
	
	 	@Override
	    public Flux<JokesRequestDto> saveAll(List<JokesRequestDto> jokes) {
	        return jokeCrudRepository.saveAll(jokes);
	    }


		@Override
		public <S extends JokesRequestDto> Mono<S> save(S entity) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public <S extends JokesRequestDto> Flux<S> saveAll(Iterable<S> entities) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public <S extends JokesRequestDto> Flux<S> saveAll(Publisher<S> entityStream) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public Mono<JokesRequestDto> findById(Integer id) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public Mono<JokesRequestDto> findById(Publisher<Integer> id) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public Mono<Boolean> existsById(Integer id) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public Mono<Boolean> existsById(Publisher<Integer> id) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public Flux<JokesRequestDto> findAll() {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public Flux<JokesRequestDto> findAllById(Iterable<Integer> ids) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public Flux<JokesRequestDto> findAllById(Publisher<Integer> idStream) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public Mono<Long> count() {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public Mono<Void> deleteById(Integer id) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public Mono<Void> deleteById(Publisher<Integer> id) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public Mono<Void> delete(JokesRequestDto entity) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public Mono<Void> deleteAllById(Iterable<? extends Integer> ids) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public Mono<Void> deleteAll(Iterable<? extends JokesRequestDto> entities) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public Mono<Void> deleteAll(Publisher<? extends JokesRequestDto> entityStream) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public Mono<Void> deleteAll() {
			// TODO Auto-generated method stub
			return null;
		}

}
