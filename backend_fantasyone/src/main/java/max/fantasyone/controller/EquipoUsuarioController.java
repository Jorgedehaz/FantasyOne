package max.fantasyone.controller;

import jakarta.persistence.EntityNotFoundException;
import max.fantasyone.dto.request.EquipoUsuarioRequestDTO;
import max.fantasyone.dto.response.EquipoUsuarioResponseDTO;
import max.fantasyone.exception.PresupuestoInsuficiente;
import max.fantasyone.mapper.EquipoUsuarioMapper;
import max.fantasyone.model.EquipoUsuario;
import max.fantasyone.service.EquipoUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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


    //POST /api/equipos -> Crea un nuevo equipo para un usuario en una liga.
    @PostMapping
    public ResponseEntity<EquipoUsuarioResponseDTO> crearEquipo(@RequestBody EquipoUsuarioRequestDTO dto) {
        EquipoUsuario equipo = equipoUsuarioService.crearEquipo(dto);
        EquipoUsuarioResponseDTO response = equipoUsuarioMapper.toDTO(equipo);
        return ResponseEntity.ok(response);
    }

    //GET /api/equipos/usuario/{usuarioId}/liga/{ligaId} → Obtiene el equipo del usuario en esa liga.
    @GetMapping("/usuario/{usuarioId}/liga/{ligaId}")
    public ResponseEntity<EquipoUsuarioResponseDTO> obtenerEquipo(@PathVariable Long usuarioId,
                                                                  @PathVariable Long ligaId) {
        EquipoUsuario equipo = equipoUsuarioService.buscarPorUsuarioYLiga(usuarioId, ligaId);
        if (equipo == null) {
            return ResponseEntity.notFound().build();
        }
        EquipoUsuarioResponseDTO response = equipoUsuarioMapper.toDTO(equipo);
        return ResponseEntity.ok(response);
    }

    //POST /api/equipos/{equipoId}/fichar?pilotoId={} → Ficha piloto y devuelve el equipo actualizado.
    @PostMapping("/{equipoId}/fichar")
    public ResponseEntity<EquipoUsuarioResponseDTO> ficharPiloto(@PathVariable Long equipoId, @RequestParam Long pilotoId) {
            EquipoUsuario equipo = equipoUsuarioService.ficharPiloto(equipoId, pilotoId);
            EquipoUsuarioResponseDTO response = equipoUsuarioMapper.toDTO(equipo);
            return ResponseEntity.ok(response);
    }

    //GET /api/equipos/clasificacion/{ligaId} → Devuelve la lista ordenada de equipos (clasificación)
    @GetMapping("/clasificacion/{ligaId}")
    public ResponseEntity<List<EquipoUsuarioResponseDTO>> getClasificacion(@PathVariable Long ligaId) {
        List<EquipoUsuario> clasificacion = equipoUsuarioService.calcularClasificacion(ligaId);
        List<EquipoUsuarioResponseDTO> dtoList = clasificacion.stream()
                .map(equipoUsuarioMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @ExceptionHandler(PresupuestoInsuficiente.class)
    public ResponseEntity<Map<String, String>> handlePresupuestoInsuficiente(PresupuestoInsuficiente ex) {
        // Devolvemos { "message": "Presupuesto insuficiente para realizar el fichaje" } con HTTP 400
        return ResponseEntity
                .badRequest()
                .body(Collections.singletonMap("message", ex.getMessage()));
    }
}
