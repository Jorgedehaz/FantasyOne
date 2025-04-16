package max.fantasyone.repository;

import max.fantasyone.model.ResultadoCarrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultadoCarreraRepository extends JpaRepository<ResultadoCarrera, Long> {
    List<ResultadoCarrera> findByPiloto_Id(Long pilotoId);
    List<ResultadoCarrera> findByCarrera_Id(Long carreraId);
    Optional<ResultadoCarrera> findByPiloto_IdAndCarrera_Id(Long pilotoId, Long carreraId);
}

