package max.fantasyone.repository;

import max.fantasyone.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByNombre(String nombre);
    List<Usuario> findByActivoTrue();
    // (Opcional, por si lo necesitas)
    List<Usuario> findByActivo(boolean activo);
    boolean existsByNombreIgnoreCase(String nombre);
    boolean existsByEmailIgnoreCase(String email);

}
