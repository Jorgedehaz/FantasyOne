package max.fantasyone.repository;

import max.fantasyone.model.Piloto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PilotoRepository extends JpaRepository<Piloto, Long> {
    Optional<Piloto> findByNombreCompleto(String nombreCompleto);
    Optional<Piloto> findByNombre(String nombre);
    List<Piloto> findByFichadoFalse();
    List<Piloto> findByMercadoId(Long mercadoId);
    List<Piloto> findByLigaId(Long ligaId);
    Optional<Piloto> findByExternalId(String externalId);
    Optional<Piloto> findByExternalIdAndLigaId(String externalId, Long ligaId);


    //Devuelve todos los externalId de los pilotos.
    @Query("SELECT p.externalId FROM Piloto p")
    List<String> findAllExternalIds();
}

