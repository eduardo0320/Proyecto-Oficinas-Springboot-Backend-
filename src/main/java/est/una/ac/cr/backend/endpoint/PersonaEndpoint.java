package est.una.ac.cr.backend.endpoint;

import est.una.ac.cr.backend.dto.PersonaType;
import est.una.ac.cr.backend.entity.Persona;
import est.una.ac.cr.backend.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


import java.util.List;

@Endpoint
public class PersonaEndpoint {

    private static final String NAMESPACE_URI = "http://soapcrud.una.ac.cr/ws/persona";

    @Autowired
    private PersonaService personaService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPersonasRequest")
    @ResponsePayload
    public GetPersonasResponse listarPersonas(@RequestPayload GetPersonasRequest request) {
        GetPersonasResponse response = new GetPersonasResponse();
        List<Persona> personas = personaService.listarPersonas();

        for (Persona persona : personas) {
            PersonaType personaType = new PersonaType();
            personaType.setIdAuto(persona.getIdAuto());
            personaType.setIdUsuario(persona.getIdUsuario());
            personaType.setNombre(persona.getNombre());
            personaType.setEmail(persona.getEmail());
            personaType.setDireccion(persona.getDireccion());
            personaType.setTelefono(persona.getTelefono());
            personaType.setCargo(persona.getCargo());
            personaType.setEstado(persona.getEstado());
            response.getPersonas().add(personaType);
        }

        return response;
    }
}