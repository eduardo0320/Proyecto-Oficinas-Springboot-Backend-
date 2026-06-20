package est.una.ac.cr.backend.controller;

import est.una.ac.cr.backend.dto.OficinaType;
import est.una.ac.cr.backend.dto.PersonaType;
import est.una.ac.cr.backend.entity.Oficina;
import est.una.ac.cr.backend.repository.OficinaRepository;
import est.una.ac.cr.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/visor/oficinas")
public class OficinaVisorController {

    @Autowired
    private OficinaRepository oficinaRepository;

    @GetMapping
    public List<OficinaType> obtenerOficinas() {
        List<Oficina> oficinas = oficinaRepository.findAll();

        return oficinas.stream()
                .map(oficina -> new OficinaType(oficina))
                .collect(Collectors.toList());
    }

    @GetMapping("/paginacion")
    public List<OficinaType> mostrarOficinas(@RequestParam(defaultValue = "0") int page){
        int size=5;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return oficinaRepository.findAll(pageable)
                .stream()
                .map(OficinaType::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/personas")
    public List<PersonaType> obtenerPersonasDeOficinas(@PathVariable Integer id) {
        return oficinaRepository.findById(id)
                .map(oficina -> oficina.getPersonas().stream()
                        .map(PersonaType::new)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }



    @GetMapping("/{id}")
    public ResponseEntity<OficinaType> obtenerPorId(@PathVariable Integer id) {
        return oficinaRepository.findById(id)
                .map(oficina -> ResponseEntity.ok(new OficinaType(oficina)))
                .orElse(ResponseEntity.notFound().build());
    }



}
