package max.fantasyone.repository;

import max.fantasyone.model.EquipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipoUsuarioRepository extends JpaRepository<EquipoUsuario, Long> {
    List<EquipoUsuario> findByUsuario_Id(Long usuarioId);
    List<EquipoUsuario> findByLiga_Id(Long ligaId);
    Optional<EquipoUsuario> findByUsuario_IdAndLiga_Id(Long usuarioId, Long ligaId);
}

