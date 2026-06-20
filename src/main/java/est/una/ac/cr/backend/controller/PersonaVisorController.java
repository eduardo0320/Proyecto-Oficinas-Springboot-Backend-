package est.una.ac.cr.backend.controller;

import est.una.ac.cr.backend.dto.OficinaType;
import est.una.ac.cr.backend.dto.PersonaType;
import est.una.ac.cr.backend.dto.Registro_IngresosType;
import est.una.ac.cr.backend.entity.Oficina;
import est.una.ac.cr.backend.entity.Persona;
import est.una.ac.cr.backend.repository.OficinaRepository;
import est.una.ac.cr.backend.repository.PersonaRepository;
import est.una.ac.cr.backend.repository.Registro_IngresosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/visor/personas")
public class PersonaVisorController {

    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    OficinaRepository oficinaRepository;
    @Autowired
    private Registro_IngresosRepository registro_IngresosRepository;

    @GetMapping
    public List<PersonaType> obtenerPersonas() {
        List<Persona> personas = personaRepository.findAll();

        return personas.stream()
                .map(persona -> new PersonaType(persona))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonaType> obtenerPorId(@PathVariable Integer id) {
        return personaRepository.findById(id)
                .map(persona -> ResponseEntity.ok(new PersonaType(persona)))
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/paginacion")
    public Page<PersonaType> mostrarPersonas(@RequestParam(defaultValue = "0") int page) {
        int size = 5;
        Pageable pageable = PageRequest.of(page, size, Sort.by("idAuto").ascending());
        Page<Persona> pagina = personaRepository.findAll(pageable);

        return pagina.map(PersonaType::new);
    }

    @GetMapping("/buscar")
    public List<PersonaType> mostrarPersonasPorCategoria(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam Integer categoria,
                                                         @RequestParam String busqueda){
        int size=5;
        Page<Persona> personas;
        Pageable pageable = PageRequest.of(page, size, Sort.by("idAuto").ascending());

        switch (categoria) {
            case 1: // ID
                try {
                    int id = Integer.parseInt(busqueda);
                    personas = personaRepository.findByIdAuto(id, pageable);
                } catch (NumberFormatException e) {
                    personas = Page.empty(); // id inválido
                }
                break;

            case 2: // Nombre
                personas = personaRepository.findByNombreContainingIgnoreCase(busqueda, pageable);
                break;

            case 3: // Email
                personas = personaRepository.findByEmailContainingIgnoreCase(busqueda, pageable);
                break;

            case 4: // Dirección
                personas = personaRepository.findByDireccionContainingIgnoreCase(busqueda, pageable);
                break;

            case 5:
                LocalDate fecha = LocalDate.parse(busqueda); // debe ser "2025-06-13"
                personas = personaRepository.findByFechaNacimiento(fecha, pageable);
                break;

            case 6: // Teléfono
                personas = personaRepository.findByTelefonoContaining(busqueda, pageable);
                break;

            case 7: // Cargo
                personas = personaRepository.findByCargoContainingIgnoreCase(busqueda, pageable);
                break;

            case 8: // Estado
                personas = personaRepository.findByEstadoContainingIgnoreCase(busqueda, pageable);
                break;

            case 9: // Oficina
                personas = personaRepository.findByOficina_NombreContainingIgnoreCase(busqueda, pageable);
                break;


            default:
                personas = personaRepository.findAll(pageable);
                break;
        }
        return personas.stream()
                .map(PersonaType::new)
                .collect(Collectors.toList());
    }



}


