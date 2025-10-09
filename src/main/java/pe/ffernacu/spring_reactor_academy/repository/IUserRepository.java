package pe.ffernacu.spring_reactor_academy.repository;

import pe.ffernacu.spring_reactor_academy.model.User;
import reactor.core.publisher.Mono;

public interface IUserRepository extends IGenericRepository<User, String>{
    //@Query("{username : ?1}") //query jqpl
    Mono<User> findOneByUsername(String username); //query derivado
}
