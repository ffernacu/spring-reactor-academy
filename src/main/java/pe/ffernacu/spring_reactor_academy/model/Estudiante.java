package pe.ffernacu.spring_reactor_academy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("estudiantes")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Estudiante {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String nombres;
    private String apellidos;
    private String dni;
    private Integer edad;
}
