package max.fantasyone.repository;

import max.fantasyone.model.Mercado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MercadoRepository extends JpaRepository<Mercado, Long> {
    Optional<Mercado> findByFecha(LocalDate fecha);
}

