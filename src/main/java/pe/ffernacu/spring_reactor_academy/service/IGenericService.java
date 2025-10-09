package pe.ffernacu.spring_reactor_academy.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IGenericService<T, ID> {
    Mono<T> findByid(ID id);
    Flux<T> findAll();
    Mono<T> save(T t);
    Mono<Boolean> delete(ID id);


}
