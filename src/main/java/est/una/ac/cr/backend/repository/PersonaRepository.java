package est.una.ac.cr.backend.repository;

import est.una.ac.cr.backend.entity.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {

    Page<Persona> findByIdAuto(int idAuto, Pageable pageable); // Solo exacto, por ser int
    Page<Persona> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
    Page<Persona> findByEmailContainingIgnoreCase(String email, Pageable pageable);
    Page<Persona> findByDireccionContainingIgnoreCase(String direccion, Pageable pageable);
    Page<Persona> findByFechaNacimiento(LocalDate fechaNacimiento, Pageable pageable);
    Page<Persona> findByTelefonoContaining(String telefono, Pageable pageable);
    Page<Persona> findByCargoContainingIgnoreCase(String cargo, Pageable pageable);
    Page<Persona> findByEstadoContainingIgnoreCase(String estado, Pageable pageable);
    Page<Persona> findByOficina_NombreContainingIgnoreCase(String nombreOficina, Pageable pageable); // Suponiendo que Oficina tiene un campo `nombre`


    @Query("SELECT p FROM Persona p WHERE p.enOficina = true")
    List<Persona> findPersonasEnOficina();

}

