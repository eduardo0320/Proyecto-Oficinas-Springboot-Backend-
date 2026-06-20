package est.una.ac.cr.backend.endpoint;

import est.una.ac.cr.backend.dto.OficinaType;
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
@XmlType(name = "GetOficinasResponse", propOrder = {"oficinas"})
@XmlRootElement(name = "getOficinasResponse")
public class GetOficinasResponse {

    @XmlElement(name = "oficina", required = true)
    private List<OficinaType> oficinas =new ArrayList<OficinaType>();

    public void setOficinas(List<OficinaType> oficinas) {
        this.oficinas = oficinas;
    }

    public List<OficinaType> getOficinas() {
        return oficinas;
    }
}