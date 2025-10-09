package pe.ffernacu.spring_reactor_academy.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.ffernacu.spring_reactor_academy.model.Role;
import pe.ffernacu.spring_reactor_academy.model.User;
import pe.ffernacu.spring_reactor_academy.repository.IGenericRepository;
import pe.ffernacu.spring_reactor_academy.repository.IRoleRepository;
import pe.ffernacu.spring_reactor_academy.repository.IUserRepository;
import pe.ffernacu.spring_reactor_academy.service.IUserService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends IGenericServiceImpl<User, String> implements IUserService {

    private final IUserRepository iUserRepository;
    private final IRoleRepository iRoleRepository;

    @Override
    public IGenericRepository<User, String> getRepository() {
        return iUserRepository;
    }

    @Override
    public Mono<pe.ffernacu.spring_reactor_academy.security.User> searchByUser(String username) {
        return iUserRepository.findOneByUsername(username)
                .zipWhen(user -> Flux
                                .fromIterable(user.getRoles())
                                .flatMap(role -> iRoleRepository.findById(role.getId()).map(Role::getName))
                        .collectList())
                .map(tuple -> {
                    User user = tuple.getT1();
                    List<String> rolesList = tuple.getT2();

                    return new pe.ffernacu.spring_reactor_academy.security.User(
                            user.getUsername(),
                            user.getPassword(),
                            rolesList,
                            user.isStatus()
                    );
                });
    }

}
