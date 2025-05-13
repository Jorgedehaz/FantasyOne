package max.fantasyone.repository;

import max.fantasyone.model.Piloto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PilotoRepository extends JpaRepository<Piloto, Long> {
    Optional<Piloto> findByNombreCompleto(String nombreCompleto);
    List<Piloto> findByFichadoFalse();
    List<Piloto> findByMercadoId(Long mercadoId);
}

