package max.fantasyone.repository;

import max.fantasyone.model.ApuestaVirtual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApuestaVirtualRepository extends JpaRepository<ApuestaVirtual, Long> {
    List<ApuestaVirtual> findByUsuario_Id(Long usuarioId);
    List<ApuestaVirtual> findByLiga_Id(Long ligaId);
    List<ApuestaVirtual> findByCarrera_Id(Long carreraId);
    List<ApuestaVirtual> findByPiloto_Id(Long pilotoId);
}

