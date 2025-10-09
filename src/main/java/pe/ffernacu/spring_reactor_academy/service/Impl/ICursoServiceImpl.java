package pe.ffernacu.spring_reactor_academy.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.ffernacu.spring_reactor_academy.model.Curso;
import pe.ffernacu.spring_reactor_academy.repository.ICursoRepository;
import pe.ffernacu.spring_reactor_academy.repository.IGenericRepository;
import pe.ffernacu.spring_reactor_academy.service.ICursoService;

@Service
@RequiredArgsConstructor
public class ICursoServiceImpl extends IGenericServiceImpl<Curso, String> implements ICursoService {

    private final ICursoRepository iCursoRepository;

    @Override
    public IGenericRepository<Curso, String> getRepository() {
        return iCursoRepository;
    }
}
