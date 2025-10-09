package pe.ffernacu.spring_reactor_academy.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.ffernacu.spring_reactor_academy.repository.IGenericRepository;
import pe.ffernacu.spring_reactor_academy.service.IGenericService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public abstract class IGenericServiceImpl<T, ID> implements IGenericService<T, ID> {

    public abstract IGenericRepository<T, ID> getRepository();

    @Override
    public Mono<T> findByid(ID id) {
        return getRepository().findById(id);
    }

    @Override
    public Flux<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public Mono<T> save(T t) {
        return getRepository().save(t);
    }

    @Override
    public Mono<Boolean> delete(ID id) {
        return getRepository().findById(id)
                .hasElement()
                .flatMap(e -> {
                    if (e) return getRepository().deleteById(id).thenReturn(true);
                    else return Mono.just(false);
                });
    }
}
