package max.fantasyone.service;

import max.fantasyone.model.Usuario;
import max.fantasyone.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public boolean existePorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    //metodo para que un Admin pueda dar de baja un User sin eliminar los datos de la BD
    public Optional<Usuario> cambiarEstadoActivo(Long id, boolean activo) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        usuarioOpt.ifPresent(usuario -> {
            usuario.setActivo(activo);
            usuarioRepository.save(usuario);
        });
        return usuarioOpt;
    }

    public List<Usuario> obtenerUsuariosActivos() {
        return usuarioRepository.findByActivoTrue();
    }

    public List<Usuario> obtenerPorEstado(boolean activo) {
        return usuarioRepository.findByActivo(activo);
    }


}

