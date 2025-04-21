package max.fantasyone.controller;

import max.fantasyone.dto.request.UsuarioRequestDTO;
import max.fantasyone.dto.response.UsuarioResponseDTO;
import max.fantasyone.mapper.UsuarioMapper;
import max.fantasyone.model.Usuario;
import max.fantasyone.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, UsuarioMapper usuarioMapper) {
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }

    // GET /api/usuarios → lista de todos los usuarios
    @GetMapping
    public List<UsuarioResponseDTO> obtenerTodos() {
        return usuarioService.obtenerTodos()
                .stream()
                .map(usuarioMapper::toDTO)
                .toList();
    }

    // GET /api/usuarios/{id} → obtener un usuario por su ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id)
                .map(usuarioMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/usuarios → registrar nuevo usuario
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> registrar(@RequestBody UsuarioRequestDTO dto) {
        if (usuarioService.existePorEmail(dto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409
        }
        Usuario nuevo = usuarioMapper.toEntity(dto);
        Usuario guardado = usuarioService.guardar(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toDTO(guardado)); // 201
    }

    // GET /api/usuarios/email/{email} → buscar usuario por email
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorEmail(@PathVariable String email) {
        return usuarioService.buscarPorEmail(email)
                .map(usuarioMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/usuarios/{id}/estado?activo=true|false
    @PutMapping("/{id}/estado")
    public ResponseEntity<UsuarioResponseDTO> cambiarEstado(@PathVariable Long id, @RequestParam boolean activo) {
        Optional<Usuario> actualizado = usuarioService.cambiarEstadoActivo(id, activo);
        return actualizado.map(usuarioMapper::toDTO).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // GET /api/usuarios/activos → solo usuarios activos
    @GetMapping("/activos")
    public List<UsuarioResponseDTO> obtenerSoloActivos() {
        return usuarioService.obtenerUsuariosActivos()
                .stream()
                .map(usuarioMapper::toDTO)
                .toList();
    }

    // GET /api/usuarios/estado?activo=true|false → filtro por estado
    @GetMapping("/estado")
    public List<UsuarioResponseDTO> obtenerPorEstado(@RequestParam boolean activo) {
        return usuarioService.obtenerPorEstado(activo)
                .stream()
                .map(usuarioMapper::toDTO)
                .toList();
    }
}
