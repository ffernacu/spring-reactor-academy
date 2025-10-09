package pe.ffernacu.spring_reactor_academy.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CursoDTO {
    private String id;
    @NotNull
    private String nombre;
    @NotNull
    private String siglas;
    private Boolean estado;
}
