package max.fantasyone.repository;

import max.fantasyone.model.Piloto;
import max.fantasyone.model.ResultadoCarrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ResultadoCarreraRepository extends JpaRepository<ResultadoCarrera, Long> {
    List<ResultadoCarrera> findByCarrera_Id(Long carreraId);
    List<ResultadoCarrera> findByCarreraId(Long carreraId);
    List<ResultadoCarrera> findBypilotoExternalId(String externalId);

    //Devuelve el último resultado (por fecha/momento) de un piloto dado su externalId.
    ResultadoCarrera findTopByPilotoExternalIdOrderByMomentoDesc(String pilotoExternalId);

    //Devuelve todos los resultados de un piloto (por externalId),ordenados por fecha de más antiguo a más nuevo.
    List<ResultadoCarrera> findByPilotoExternalIdOrderByMomentoAsc(String pilotoExternalId);


    //Suma todos los puntosFantasy de los resultados de un piloto.
    @Query("SELECT COALESCE(SUM(r.puntosFantasy), 0) FROM ResultadoCarrera r WHERE r.pilotoExternalId = :extId")
    int sumPuntosByPilotoExternalId(@Param("extId") String pilotoExternalId);

    // Busca todos los resultados de un pilotoExternalId en una carrera concreta
    @Query("""
            SELECT r FROM ResultadoCarrera r
            WHERE r.carrera.id = :carreraId
            AND r.pilotoExternalId = :externalId""")
    List<ResultadoCarrera> findByCarreraIdAndDriverExternalId(@Param("carreraId") Long carreraId,
            @Param("pilotoExternalId") String driverExternalId);
    // Y si necesitas un histórico a partir de una fecha (campo momento):
    List<ResultadoCarrera> findByPilotoExternalIdAndMomentoAfter(
            String pilotoExternalId,
            LocalDateTime desde);
}

