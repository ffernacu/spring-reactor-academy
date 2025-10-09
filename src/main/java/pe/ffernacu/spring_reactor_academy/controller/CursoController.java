package pe.ffernacu.spring_reactor_academy.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.ffernacu.spring_reactor_academy.dto.CursoDTO;
import pe.ffernacu.spring_reactor_academy.model.Curso;
import pe.ffernacu.spring_reactor_academy.service.ICursoService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/v1/courses")
@RequiredArgsConstructor
public class CursoController {

    private final ICursoService iCursoService;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;


    @GetMapping("/{id}")
    public Mono<ResponseEntity<CursoDTO>> findById(@PathVariable String id){
        return iCursoService.findByid(id)
                .map(this::mapToDTO)
                .map(e -> ResponseEntity
                        .ok()
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<CursoDTO>> save(@Valid @RequestBody CursoDTO cursoDTO){
        return iCursoService.save(mapToModel(cursoDTO))
                .map(this::mapToDTO)
                .map(e -> ResponseEntity
                        .created(URI.create("/save"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e));
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<CursoDTO>>> findAll(){
        Flux<CursoDTO> fx = iCursoService.findAll()
                .map(this::mapToDTO);
        return Mono.just(ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fx));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<CursoDTO>> update(@Valid @RequestBody CursoDTO cursoDTO, @PathVariable String id){
        return iCursoService.findByid(id)
                .flatMap(e -> {
                    cursoDTO.setId(id);
                    return iCursoService.save(mapToModel(cursoDTO));
                })
                .map(this::mapToDTO)
                .map(e -> ResponseEntity
                        .accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Boolean>> delete(@PathVariable String id){
        return iCursoService.delete(id)
                .map(e -> {
                    if(e){
                        return ResponseEntity.noContent().build();
                    }
                    else return ResponseEntity.notFound().build();
                });
    }

    private Curso mapToModel(CursoDTO cursoDTO){
        return modelMapper.map(cursoDTO, Curso.class);
    };

    private CursoDTO mapToDTO(Curso curso){
        return modelMapper.map(curso, CursoDTO.class);
    };
}
