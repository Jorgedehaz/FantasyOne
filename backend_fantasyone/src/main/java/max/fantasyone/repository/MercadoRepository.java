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

    //Query para obtener la lista de pilotos diaria por liga (Mercado)
    @Query("SELECT pm FROM PilotoMercado pm WHERE pm.mercado.liga.id = :ligaId AND pm.fichado = false")
    List<Piloto> obtenerPilotosDelMercadoPorLiga(@Param("ligaId") Long ligaId);

    Optional<Mercado> findByLigaId(Long ligaId);
}

