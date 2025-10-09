package pe.ffernacu.spring_reactor_academy.handler;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.ffernacu.spring_reactor_academy.dto.MatriculaDTO;
import pe.ffernacu.spring_reactor_academy.model.Matricula;
import pe.ffernacu.spring_reactor_academy.service.ICursoService;
import pe.ffernacu.spring_reactor_academy.service.IEstudianteService;
import pe.ffernacu.spring_reactor_academy.service.IMatriculaService;
import pe.ffernacu.spring_reactor_academy.validator.RequestValidator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class MatriculaHandler {
    private final IMatriculaService iMatriculaService;

    private final IEstudianteService iEstudianteService;

    private final ICursoService iCursoService;
    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;

    private final RequestValidator requestValidator;

    public Mono<ServerResponse> save(ServerRequest serverRequest){
        Mono<MatriculaDTO> monoMatriculaDTO = serverRequest.bodyToMono(MatriculaDTO.class);
        return monoMatriculaDTO
                 .flatMap(requestValidator::validate)
                 .map(this::maptoModel)
                 .flatMap(iMatriculaService::save)
                 .map(this::maptoDTO)
                 .flatMap(e -> ServerResponse
                         .created(URI.create(serverRequest.uri().toString().concat("/").concat(e.getId())))
                         .contentType(MediaType.APPLICATION_JSON)
                         .body(BodyInserters.fromValue(e)));

    }
    public Mono<ServerResponse> report(ServerRequest serverRequest){
        var matriculaFlux = iMatriculaService.findAll()
                .flatMap(this::getMatriculaMonoWithStudents)
                .flatMap(this::getMatriculaMonoWithCourses);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(matriculaFlux.map(this::maptoDTO), MatriculaDTO.class);
    }

    private Mono<Matricula> getMatriculaMonoWithCourses(Matricula matricula) {
        System.out.println("matricula." + matricula.getId());
        if(matricula.getCursos() != null && !matricula.getCursos().isEmpty()) {
            var cursoList = matricula.getCursos();
            var cursoFlux = Flux.fromIterable(cursoList);
            var x = cursoFlux.flatMap(curso -> {
                System.out.println("curso: " + curso.getId());
                return iCursoService.findByid(curso.getId());
            });
            var y = x.collectList();
            return y.zipWith(Mono.just(matricula), (cursoListT1, matriculaT2) -> {
                matriculaT2.setCursos(cursoListT1);
                return matriculaT2;
            });
        }
        else return Mono.just(matricula);
    }

    private Mono<Matricula> getMatriculaMonoWithStudents(Matricula e) {
        if(e.getEstudiante() != null){
            return Mono.just(e)
                    .flatMap(matricula ->
                            iEstudianteService.findByid(matricula.getEstudiante().getId())
                                    .zipWith(Mono.just(matricula), (estudianteT1, matriculaT2) -> {
                                        matriculaT2.setEstudiante(estudianteT1);
                                        return matriculaT2;
                                    })
                    );
        }
        else return Mono.just(e);
    }

    private Matricula maptoModel(MatriculaDTO matriculaDTO){
        return modelMapper.map(matriculaDTO, Matricula.class);
    };

    private MatriculaDTO maptoDTO(Matricula matricula){
        return modelMapper.map(matricula, MatriculaDTO.class);
    };
}
