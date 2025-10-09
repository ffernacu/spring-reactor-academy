package pe.ffernacu.spring_reactor_academy.service;

import pe.ffernacu.spring_reactor_academy.model.User;
import reactor.core.publisher.Mono;

public interface IUserService extends IGenericService<User, String>{
    Mono<pe.ffernacu.spring_reactor_academy.security.User> searchByUser(String username);
}
