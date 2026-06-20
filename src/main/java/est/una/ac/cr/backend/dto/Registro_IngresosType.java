package est.una.ac.cr.backend.dto;

import est.una.ac.cr.backend.entity.Registro_Ingresos;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Registro_IngresosType", propOrder = {
        "id",
        "tipo",
        "fecha",
        "Hora",
        "persona"
})
public class Registro_IngresosType {

    private Integer id;
    private String tipo;
    private LocalDate fecha;
    private LocalTime Hora;
    private String persona;


    public Registro_IngresosType(Registro_Ingresos registroIngresos) {
        this.id = registroIngresos.getId();
        this.tipo = registroIngresos.getTipo();
        this.fecha = registroIngresos.getFecha();
        Hora = registroIngresos.getHora();

        if(registroIngresos.getPersona()!= null){
            this.persona=registroIngresos.getPersona().getNombre();
        } else {
            this.persona="";
        }
    }

    public Registro_IngresosType() {
    }
}