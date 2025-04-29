package max.fantasyone.service;

import max.fantasyone.dto.request.RegistroRequestDTO;
import max.fantasyone.model.Liga;
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

    public Usuario registrarUsuario(RegistroRequestDTO dto) {
        // Validar nombre
        if (dto.getNombre() == null || dto.getNombre().length() < 3 || !dto.getNombre().matches("[a-zA-Z0-9]+")) {
            throw new IllegalArgumentException("El nombre debe tener al menos 3 caracteres y solo contener letras o números.");
        }

        // Validar email
        if (!dto.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("El email no tiene un formato válido.");
        }

        // Verificar si el nombre o email ya existen
        if (usuarioRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso.");
        }

        if (usuarioRepository.existsByEmailIgnoreCase(dto.getEmail())) {
            throw new IllegalArgumentException("El email ya está en uso.");
        }

        // Comprobar contraseñas
        if (!dto.getPassword().equals(dto.getPassword2())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden.");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword()); // Puedes encriptar si lo deseas
        usuario.setRol("USER");
        usuario.setActivo(true);

        return usuarioRepository.save(usuario);
    }

    // Usamos el metodo del repository que devuelve todas las ligas y volcamos en una lista las que sean privadas
    public List<Liga> obtenerLigasPrivadas(Long usuarioId) {
        return usuarioRepository.findLigasByUsuarioId(usuarioId).stream()
                .filter(Liga::isPrivada)
                .toList();
    }
}

