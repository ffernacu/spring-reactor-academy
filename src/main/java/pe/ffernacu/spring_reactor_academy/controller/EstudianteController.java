package pe.ffernacu.spring_reactor_academy.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.ffernacu.spring_reactor_academy.dto.EstudianteDTO;
import pe.ffernacu.spring_reactor_academy.model.Estudiante;
import pe.ffernacu.spring_reactor_academy.service.IEstudianteService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/v1/students")
@RequiredArgsConstructor
public class EstudianteController {

    private final IEstudianteService iEstudianteService;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;


    @GetMapping("/{id}")
    public Mono<ResponseEntity<EstudianteDTO>> findById(@PathVariable String id){
        return iEstudianteService.findByid(id)
                .map(this::mapToDTO)
                .map(e -> ResponseEntity
                        .ok()
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<EstudianteDTO>> save(@Valid @RequestBody EstudianteDTO estudianteDTO){
        return iEstudianteService.save(mapToModel(estudianteDTO))
                .map(this::mapToDTO)
                .map(e -> ResponseEntity
                        .created(URI.create("/save"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e));
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<EstudianteDTO>>> findAll(){
        Flux<EstudianteDTO> fx = iEstudianteService.findAll()
                .map(this::mapToDTO);
        return Mono.just(ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fx));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<EstudianteDTO>> update(@Valid @RequestBody EstudianteDTO estudianteDTO, @PathVariable String id){
        return iEstudianteService.findByid(id)
                .flatMap(e -> {
                    estudianteDTO.setId(id);
                    return iEstudianteService.save(mapToModel(estudianteDTO));
                })
                .map(this::mapToDTO)
                .map(e -> ResponseEntity
                        .accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Boolean>> delete(@PathVariable String id){
        return iEstudianteService.delete(id)
                .map(e -> {
                    if(e){
                        return ResponseEntity.noContent().build();
                    }
                    else return ResponseEntity.notFound().build();
                });
    }

    @GetMapping("/orderByAge")
    public Mono<ResponseEntity<Flux<EstudianteDTO>>> findAllByDescendingOrder(@RequestParam(defaultValue = "false") Boolean isAscending){
        Flux<EstudianteDTO> fx = iEstudianteService.findAllOrderByAge(isAscending)
                .map(this::mapToDTO);
        return Mono.just(ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fx));
    }

    private Estudiante mapToModel(EstudianteDTO estudianteDTO){
        return modelMapper.map(estudianteDTO, Estudiante.class);
    };

    private EstudianteDTO mapToDTO(Estudiante estudiante){
        return modelMapper.map(estudiante, EstudianteDTO.class);
    };
}
