package pe.ffernacu.spring_reactor_academy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document("matriculas")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Matricula {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    private LocalDateTime fechaMatricula;
    private Estudiante estudiante;
    private List<Curso> cursos;
    private Boolean estado;
}
