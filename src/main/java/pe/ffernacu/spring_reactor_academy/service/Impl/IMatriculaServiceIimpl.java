package pe.ffernacu.spring_reactor_academy.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.ffernacu.spring_reactor_academy.model.Matricula;
import pe.ffernacu.spring_reactor_academy.repository.IGenericRepository;
import pe.ffernacu.spring_reactor_academy.repository.IMatriculaRepository;
import pe.ffernacu.spring_reactor_academy.service.IMatriculaService;

@Service
@RequiredArgsConstructor
public class IMatriculaServiceIimpl extends  IGenericServiceImpl<Matricula, String> implements IMatriculaService {

    private final IMatriculaRepository iMatriculaRepository;

    @Override
    public IGenericRepository<Matricula, String> getRepository() {
        return iMatriculaRepository;
    }

}
