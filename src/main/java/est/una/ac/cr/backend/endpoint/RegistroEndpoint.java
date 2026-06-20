package est.una.ac.cr.backend.endpoint;

import est.una.ac.cr.backend.dto.Registro_IngresosType;
import est.una.ac.cr.backend.entity.Registro_Ingresos;

import est.una.ac.cr.backend.service.Registro_IngresosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


import java.util.List;

@Endpoint
public class RegistroEndpoint {

    private static final String NAMESPACE_URI = "http://soapcrud.una.ac.cr/ws/registro";

    @Autowired
    private Registro_IngresosService registro_IngresosService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getRegistrosRequest")
    @ResponsePayload
    public GetRegistrosResponse listarRegistros(@RequestPayload GetRegistrosRequest request) {
        GetRegistrosResponse response = new GetRegistrosResponse();
        List<Registro_Ingresos> registros = registro_IngresosService.listarRegistros();

        for (Registro_Ingresos registro : registros) {
            Registro_IngresosType registroIngresosType = new Registro_IngresosType();
            registroIngresosType.setId(registro.getId());
            registroIngresosType.setTipo(registro.getTipo());
            response.getRegistros().add(registroIngresosType);
        }

        return response;
    }
}