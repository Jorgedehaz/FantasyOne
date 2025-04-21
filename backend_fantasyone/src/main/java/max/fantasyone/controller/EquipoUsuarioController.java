package max.fantasyone.controller;

import max.fantasyone.dto.request.EquipoUsuarioRequestDTO;
import max.fantasyone.dto.response.EquipoUsuarioResponseDTO;
import max.fantasyone.mapper.EquipoUsuarioMapper;
import max.fantasyone.model.EquipoUsuario;
import max.fantasyone.service.EquipoUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/equipos")
public class EquipoUsuarioController {

    private final EquipoUsuarioService equipoUsuarioService;
    private final EquipoUsuarioMapper equipoUsuarioMapper;

    @Autowired
    public EquipoUsuarioController(EquipoUsuarioService equipoUsuarioService,
                                   EquipoUsuarioMapper equipoUsuarioMapper) {
        this.equipoUsuarioService = equipoUsuarioService;
        this.equipoUsuarioMapper = equipoUsuarioMapper;
    }

    // GET /api/equipos → obtener todos los equipos
    @GetMapping
    public List<EquipoUsuarioResponseDTO> obtenerTodos() {
        return equipoUsuarioService.obtenerTodos()
                .stream()
                .map(equipoUsuarioMapper::toDTO)
                .toList();
    }

    // GET /api/equipos/usuario/{usuarioId} → equipos por usuario
    @GetMapping("/usuario/{usuarioId}")
    public List<EquipoUsuarioResponseDTO> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return equipoUsuarioService.buscarPorUsuario(usuarioId)
                .stream()
                .map(equipoUsuarioMapper::toDTO)
                .toList();
    }

    // GET /api/equipos/liga/{ligaId} → equipos en una liga
    @GetMapping("/liga/{ligaId}")
    public List<EquipoUsuarioResponseDTO> obtenerPorLiga(@PathVariable Long ligaId) {
        return equipoUsuarioService.buscarPorLiga(ligaId)
                .stream()
                .map(equipoUsuarioMapper::toDTO)
                .toList();
    }

    // GET /api/equipos/usuario/{usuarioId}/liga/{ligaId}
    @GetMapping("/usuario/{usuarioId}/liga/{ligaId}")
    public ResponseEntity<EquipoUsuarioResponseDTO> obtenerPorUsuarioYLiga(@PathVariable Long usuarioId, @PathVariable Long ligaId) {
        return equipoUsuarioService.buscarPorUsuarioYLiga(usuarioId, ligaId)
                .map(equipoUsuarioMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/equipos
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody EquipoUsuarioRequestDTO dto) {
        // Validar si ya existe equipo para usuario + liga
        Optional<EquipoUsuario> existente = equipoUsuarioService.buscarPorUsuarioYLiga(
                dto.getUsuarioId(), dto.getLigaId());

        if (existente.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un equipo en esa liga para ese usuario.");
        }

        EquipoUsuario equipo = equipoUsuarioMapper.toEntity(dto);
        EquipoUsuario guardado = equipoUsuarioService.guardar(equipo);
        return ResponseEntity.status(HttpStatus.CREATED).body(equipoUsuarioMapper.toDTO(guardado));
    }

    // DELETE /api/equipos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (equipoUsuarioService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        equipoUsuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
