package est.una.ac.cr.backend.endpoint;

import est.una.ac.cr.backend.dto.Registro_IngresosType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetRegistrosResponse", propOrder = {"registros"})
@XmlRootElement(name = "getRegistrosResponse")
public class GetRegistrosResponse {

    @XmlElement(name = "registro", required = true)
    private List<Registro_IngresosType> registros =new ArrayList<Registro_IngresosType>();

    public void setRegistros(List<Registro_IngresosType> registros) {
        this.registros = registros;
    }

    public List<Registro_IngresosType> getRegistros() {
        return registros;
    }
}