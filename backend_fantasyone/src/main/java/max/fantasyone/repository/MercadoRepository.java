package max.fantasyone.repository;

import max.fantasyone.model.Mercado;
import max.fantasyone.model.Piloto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MercadoRepository extends JpaRepository<Mercado, Long> {
    Optional<Mercado> findByFecha(LocalDate fecha);
    Optional<Mercado> findByLigaIdAndFecha(Long ligaId, LocalDate fecha);

    Optional<Mercado> findByLigaId(Long ligaId);
}

