package max.fantasyone.repository;

import max.fantasyone.model.Liga;
import max.fantasyone.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByNombre(String nombre);
    boolean existsByNombreIgnoreCase(String nombre);
    boolean existsByEmailIgnoreCase(String email);
    List<Usuario> findByActivoTrue();

    // (Opcional, por si lo necesitas)
    List<Usuario> findByActivo(boolean activo);

    //Selecciona las ligas en las que participa un usuario por su Id
    @Query("SELECT u.ligas FROM Usuario u WHERE u.id = :usuarioId")
    List<Liga> findLigasByUsuarioId(Long usuarioId);
}
