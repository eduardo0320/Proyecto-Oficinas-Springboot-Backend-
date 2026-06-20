package est.una.ac.cr.backend.controller;

import est.una.ac.cr.backend.dto.PersonaType;
import est.una.ac.cr.backend.entity.Persona;
import est.una.ac.cr.backend.entity.Oficina;
import est.una.ac.cr.backend.repository.PersonaRepository;
import est.una.ac.cr.backend.repository.OficinaRepository;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/personas")
public class PersonaAdminController {

    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    OficinaRepository oficinaRepository;



    @PostMapping
    public PersonaType crearPersona(@RequestBody Persona persona) {
        Optional<Oficina> oficinaOpt = oficinaRepository.findById(persona.getOficina().getId());

        if (oficinaOpt.isPresent()) {
            Oficina oficina = oficinaOpt.get();
            persona.setOficina(oficina);

            oficina.getPersonas().add(persona);
            persona.setEnOficina(false);
            personaRepository.save(persona);

            return new PersonaType(persona);

        } else {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Oficina no encontrada");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> actualizarPersona(@PathVariable Integer id, @RequestBody Persona personaActualizada) {
        return personaRepository.findById(id)
                .map(persona -> {
                    persona.setNombre(personaActualizada.getNombre());
                    persona.setEmail(personaActualizada.getEmail());
                    persona.setDireccion(personaActualizada.getDireccion());
                    persona.setFechaNacimiento(personaActualizada.getFechaNacimiento());
                    persona.setIdUsuario(personaActualizada.getIdUsuario());
                    persona.setOficina(personaActualizada.getOficina());
                    persona.setTelefono(personaActualizada.getTelefono());
                    persona.setCargo(personaActualizada.getCargo());
                    persona.setEstado(personaActualizada.getEstado());
                    return ResponseEntity.ok(personaRepository.save(persona));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPersona(@PathVariable Integer id) {
        if (personaRepository.existsById(id)) {
            personaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

