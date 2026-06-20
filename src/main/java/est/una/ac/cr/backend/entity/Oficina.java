package est.una.ac.cr.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Oficina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    private String direccion;
    private Integer cantidad_maxima;
    private Integer ingresos_activos;
    private Integer maximoIngresoSimultaneo;

    @OneToMany(mappedBy = "oficina")
    private List<Persona> personas;

}



