package max.fantasyone.service;

import max.fantasyone.model.EquipoUsuario;
import max.fantasyone.repository.EquipoUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipoUsuarioService {

    private final EquipoUsuarioRepository equipoUsuarioRepository;

    @Autowired
    public EquipoUsuarioService(EquipoUsuarioRepository equipoUsuarioRepository) {
        this.equipoUsuarioRepository = equipoUsuarioRepository;
    }

    public List<EquipoUsuario> obtenerTodos() {
        return equipoUsuarioRepository.findAll();
    }

    public Optional<EquipoUsuario> obtenerPorId(Long id) {
        return equipoUsuarioRepository.findById(id);
    }

    public EquipoUsuario guardar(EquipoUsuario equipo) {
        return equipoUsuarioRepository.save(equipo);
    }

    public void eliminar(Long id) {
        equipoUsuarioRepository.deleteById(id);
    }

    public Optional<EquipoUsuario> buscarPorUsuarioYLiga(Long usuarioId, Long ligaId) {
        return equipoUsuarioRepository.findByUsuario_IdAndLiga_Id(usuarioId, ligaId);
    }
}
