package est.una.ac.cr.backend.repository;

import est.una.ac.cr.backend.entity.Oficina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OficinaRepository extends JpaRepository<Oficina, Integer> {



}

