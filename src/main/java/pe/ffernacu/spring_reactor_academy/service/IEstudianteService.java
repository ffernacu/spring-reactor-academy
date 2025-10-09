package pe.ffernacu.spring_reactor_academy.service;

import pe.ffernacu.spring_reactor_academy.model.Estudiante;
import reactor.core.publisher.Flux;

public interface IEstudianteService extends IGenericService<Estudiante, String>{
    Flux<Estudiante> findAllOrderByAge(boolean isAscending);
}
