package max.fantasyone.mapper;

import max.fantasyone.dto.request.UsuarioRequestDTO;
import max.fantasyone.dto.response.UsuarioResponseDTO;
import max.fantasyone.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());
        usuario.setRol(dto.getRol());
        usuario.setActivo(true); // por defecto al crear
        return usuario;
    }

    public UsuarioResponseDTO toDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol(),
                usuario.isActivo()
        );
    }
}

