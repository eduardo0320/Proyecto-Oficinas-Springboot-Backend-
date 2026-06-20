package est.una.ac.cr.backend.controller;

import est.una.ac.cr.backend.dto.OficinaType;
import est.una.ac.cr.backend.dto.PersonaType;
import est.una.ac.cr.backend.dto.Registro_IngresosType;
import est.una.ac.cr.backend.entity.Oficina;
import est.una.ac.cr.backend.entity.Persona;
import est.una.ac.cr.backend.entity.Registro_Ingresos;
import est.una.ac.cr.backend.repository.OficinaRepository;
import est.una.ac.cr.backend.repository.PersonaRepository;
import est.una.ac.cr.backend.repository.Registro_IngresosRepository;
import est.una.ac.cr.backend.service.OficinaService;
import est.una.ac.cr.backend.service.Registro_IngresosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api")
public class Registro_IngresosController {

    @Autowired
    private Registro_IngresosRepository registro_IngresosRepository;
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private Registro_IngresosService registro_IngresosService;
    @Autowired
    private OficinaRepository oficinaRepository;

    @GetMapping("/registros")
    public List<Registro_IngresosType> obtenerRegistro_Ingresos() {
        List<Registro_Ingresos> registro_Ingresos = registro_IngresosRepository.findAll();

        return registro_Ingresos.stream()
                .map(registro_Ingreso -> new Registro_IngresosType(registro_Ingreso))
                .collect(Collectors.toList());
    }

    @GetMapping("/paginacion")
    public List<Registro_IngresosType> mostrarRegistros(@RequestParam(defaultValue = "0") int page) {
        int size = 5;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return registro_IngresosRepository.findAll(pageable)
                .stream()
                .map(Registro_IngresosType::new)
                .collect(Collectors.toList());
    }


    @GetMapping("/registros/{id}")
    public ResponseEntity<Registro_IngresosType> obtenerPorId(@PathVariable Integer id) {
        return registro_IngresosRepository.findById(id)
                .map(registro -> ResponseEntity.ok(new Registro_IngresosType(registro)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/registrador/registros")
    public Boolean crearRegistro_Ingreso(@RequestBody Registro_Ingresos registro_Ingreso) {
        int idPersona = registro_Ingreso.getPersona().getIdAuto();
        Optional<Persona> personaOpt = personaRepository.findById(idPersona);

        if (personaOpt.isPresent()) {
            Persona persona = personaOpt.get();
            registro_Ingreso.setPersona(persona);

            if (registro_IngresosService.RegistrarIngreso(registro_Ingreso)) {

                registro_IngresosRepository.save(registro_Ingreso);
                return true;
            }

            return false;

        } else {
            return false;
        }
    }


    @DeleteMapping("/registrador/registros/{id}")
    public ResponseEntity<Void> eliminarRegistro_ingreso(@PathVariable Integer id) {
        if (registro_IngresosRepository.existsById(id)) {
            registro_IngresosRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/registrador/registros/personas")
    public List<PersonaType> obtenerPersonas() {
        List<Persona> personas = personaRepository.findAll();

        return personas.stream()
                .map(persona -> new PersonaType(persona))
                .collect(Collectors.toList());
    }

    @GetMapping("/registros/top3-entradas")
    public ResponseEntity<List<Map<String, Object>>> obtenerTop3Entradas() {
        List<Object[]> resultados = registro_IngresosRepository.findTop3PersonasConMasEntradas();

        List<Map<String, Object>> respuesta = resultados.stream().map(fila -> {
            Map<String, Object> map = new HashMap<>();
            map.put("nombre", fila[0]);
            map.put("totalEntradas", fila[1]);
            return map;
        }).toList();

        return ResponseEntity.ok(respuesta);
    }


    @GetMapping("/registros/en-oficina")
    public ResponseEntity<List<PersonaType>> obtenerPersonasEnOficina() {
        List<Persona> personas = personaRepository.findPersonasEnOficina();
        List<PersonaType> resultado = personas.stream()
                .map(PersonaType::new)
                .toList();

        return ResponseEntity.ok(resultado);
    }


    @GetMapping("/registros/ocupacion-maxima")
    public ResponseEntity<List<Map<String, Object>>> obtenerOcupacionMaximaOficinas() {
        List<Oficina> oficinas = oficinaRepository.findAll();

        List<Map<String, Object>> respuesta = oficinas.stream().map(oficina -> {
            Map<String, Object> map = new HashMap<>();
            map.put("nombre", oficina.getNombre());
            map.put("maximoIngresoSimultaneo", oficina.getMaximoIngresoSimultaneo());
            return map;
        }).toList();

        return ResponseEntity.ok(respuesta);
    }
}