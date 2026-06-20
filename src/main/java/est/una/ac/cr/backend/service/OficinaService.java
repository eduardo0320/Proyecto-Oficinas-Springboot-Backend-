package est.una.ac.cr.backend.service;


import est.una.ac.cr.backend.entity.Oficina;
import est.una.ac.cr.backend.repository.OficinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OficinaService {

    @Autowired
    private OficinaRepository oficinaRepository;

    public List<Oficina> listarOficinas() {
        return oficinaRepository.findAll();
    }

    public int contarIngresosActivos(Oficina oficina) {
        int ingresosActuales = (int) oficina.getPersonas().stream()
                .filter(p -> Boolean.TRUE.equals(p.getEnOficina()))
                .count();

        Integer maximoActual = oficina.getMaximoIngresoSimultaneo();
        if (maximoActual == null || ingresosActuales > maximoActual) {
            oficina.setMaximoIngresoSimultaneo(ingresosActuales);
        }

        return ingresosActuales;
    }


}