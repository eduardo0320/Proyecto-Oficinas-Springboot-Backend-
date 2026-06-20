package est.una.ac.cr.backend.controller;

import est.una.ac.cr.backend.dto.OficinaType;
import est.una.ac.cr.backend.entity.Oficina;
import est.una.ac.cr.backend.repository.OficinaRepository;
import est.una.ac.cr.backend.service.OficinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController

@RequestMapping("/api/admin/oficinas")
public class OficinaAdminController {

    @Autowired
    private OficinaRepository oficinaRepository;


    @PostMapping
    public OficinaType crearOficina(@RequestBody Oficina oficina) {
        oficina.setIngresos_activos(0);
        oficina.setMaximoIngresoSimultaneo(0);
        oficinaRepository.save(oficina);

        return new OficinaType(oficina);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OficinaType> actualizarOficina(@PathVariable Integer id, @RequestBody Oficina oficinaActualizada) {
        return oficinaRepository.findById(id)
                .map(oficina -> {
                    oficina.setNombre(oficinaActualizada.getNombre());
                    oficina.setDireccion(oficinaActualizada.getDireccion());
                    oficina.setCantidad_maxima(oficinaActualizada.getCantidad_maxima());
                    oficina.setIngresos_activos(oficinaActualizada.getIngresos_activos());

                    Oficina oficinaGuardada = oficinaRepository.save(oficina);
                    OficinaType oficinaType = new OficinaType(oficinaGuardada);

                    return ResponseEntity.ok(oficinaType);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOficina(@PathVariable Integer id) {
        if (oficinaRepository.existsById(id)) {
            oficinaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

