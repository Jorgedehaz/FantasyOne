package max.fantasyone.repository;

import max.fantasyone.model.PilotoMercado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PilotoMercadoRepository extends JpaRepository<PilotoMercado, Long> {

    List<PilotoMercado> findByMercadoId(Long mercadoId);
    List<PilotoMercado> findByMercado_Liga_Id(Long ligaId);
    boolean existsByPilotoIdAndMercadoId(Long pilotoId, Long mercadoId);
}


