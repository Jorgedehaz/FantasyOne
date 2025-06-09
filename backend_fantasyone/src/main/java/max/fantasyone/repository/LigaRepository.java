package max.fantasyone.repository;

import max.fantasyone.model.Liga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LigaRepository extends JpaRepository<Liga, Long> {
    Optional<Liga> findByNombre(String nombre);
    List<Liga> findByPrivada(boolean privada);
    Optional<Liga> findByNombreAndCodigoAcceso(String nombre, String codigoAcceso);
}

