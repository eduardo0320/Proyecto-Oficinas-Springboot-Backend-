package est.una.ac.cr.backend.service;

import est.una.ac.cr.backend.entity.Oficina;
import est.una.ac.cr.backend.entity.Registro_Ingresos;
import est.una.ac.cr.backend.repository.OficinaRepository;
import est.una.ac.cr.backend.repository.PersonaRepository;
import est.una.ac.cr.backend.repository.Registro_IngresosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Registro_IngresosService {

    @Autowired
    private Registro_IngresosRepository registro_IngresosRepository;
    @Autowired
    private OficinaService oficinaService;
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private OficinaRepository oficinaRepository;



    public List<Registro_Ingresos> listarRegistros() {
        return registro_IngresosRepository.findAll();
    }
    public Boolean RegistrarIngreso(Registro_Ingresos registro) {
        Oficina oficina = registro.getPersona().getOficina();
        int cantMax = oficina.getCantidad_maxima();
        int cantActual = oficinaService.contarIngresosActivos(oficina);

        if (registro.getTipo().equals("Entrada") && registro.getPersona().getEnOficina().equals(false)) {

            if (cantActual < cantMax) {
                registro.getPersona().setEnOficina(true);
                personaRepository.save(registro.getPersona());
                oficina.setIngresos_activos(cantActual + 1);
                oficina.setMaximoIngresoSimultaneo(oficinaService.contarIngresosActivos(oficina));
                oficinaRepository.save(oficina);
                return true;
            }
            return false;
        }
        if (registro.getTipo().equals("Salida") && registro.getPersona().getEnOficina().equals(true)) {
            registro.getPersona().setEnOficina(false);
            personaRepository.save(registro.getPersona());
            oficina.setIngresos_activos(cantActual - 1);
            oficinaRepository.save(oficina);

            return true;
        }
        return false;

    }

}