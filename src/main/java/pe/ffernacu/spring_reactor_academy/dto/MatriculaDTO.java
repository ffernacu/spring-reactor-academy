package pe.ffernacu.spring_reactor_academy.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatriculaDTO {
    private String id;
    @NotNull
    private LocalDateTime fechaMatricula;
    private EstudianteDTO estudiante;
    private List<CursoDTO> cursos;
    private Boolean estado;
}
