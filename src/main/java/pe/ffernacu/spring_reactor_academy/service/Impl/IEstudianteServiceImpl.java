package pe.ffernacu.spring_reactor_academy.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import pe.ffernacu.spring_reactor_academy.model.Estudiante;
import pe.ffernacu.spring_reactor_academy.repository.IEstudianteRepository;
import pe.ffernacu.spring_reactor_academy.repository.IGenericRepository;
import pe.ffernacu.spring_reactor_academy.service.IEstudianteService;
import reactor.core.publisher.Flux;


@Service
@RequiredArgsConstructor
public class IEstudianteServiceImpl extends IGenericServiceImpl<Estudiante, String> implements IEstudianteService {

    private final IEstudianteRepository iEstudianteRepository;

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public IGenericRepository<Estudiante, String> getRepository() {
        return iEstudianteRepository;
    }

    @Override
    public Flux<Estudiante> findAllOrderByAge(boolean isAscending) {
        Query query = new Query();
        if(isAscending){
            query.with(Sort.by(Sort.Direction.ASC,"edad"));
        }
        else {
            query.with(Sort.by(Sort.Direction.DESC,"edad"));
        }
        return reactiveMongoTemplate.find(query, Estudiante.class);
    }

}
