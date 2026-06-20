package est.una.ac.cr.backend.endpoint;

import est.una.ac.cr.backend.dto.PersonaType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//import javax.xml.bind.annotation.*;
//import java.util.List;
//
//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlRootElement(name = "getPersonasResponse", namespace = "http://soapcrud.una.ac.cr/ws/persona")
//public class GetPersonasResponse {
//
//    @XmlElement(name = "persona")
//    private List<PersonaDTO> personas;
//
//    public List<PersonaDTO> getPersonas() {
//        return personas;
//    }
//
//    public void setPersonas(List<PersonaDTO> personas) {
//        this.personas = personas;
//    }
//}


@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetPersonasResponse", propOrder = {"personas"})
@XmlRootElement(name = "getPersonasResponse")
public class GetPersonasResponse {

    @XmlElement(name = "persona", required = true)
    private List<PersonaType> personas =new ArrayList<PersonaType>();

    public void setPersonas(List<PersonaType> personas) {
        this.personas = personas;
    }

    public List<PersonaType> getPersonas() {
        return personas;
    }
}