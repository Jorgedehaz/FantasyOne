package max.fantasyone.controller;

import max.fantasyone.dto.request.LigaRequestDTO;
import max.fantasyone.dto.request.UnirseLigaPrivadaRequestDTO;
import max.fantasyone.dto.request.UnirseLigaRequestDTO;
import max.fantasyone.dto.response.EquipoUsuarioResponseDTO;
import max.fantasyone.dto.response.LigaResponseDTO;
import max.fantasyone.mapper.LigaMapper;
import max.fantasyone.model.Liga;
import max.fantasyone.service.LigaService;
import max.fantasyone.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ligas")
public class LigaController {

    private final LigaService ligaService;
    private final LigaMapper ligaMapper;
    private final UsuarioService usuarioService;

    @Autowired
    public LigaController(LigaService ligaService, UsuarioService usuarioService, LigaMapper ligaMapper) {
        this.ligaService = ligaService;
        this.usuarioService = usuarioService;
        this.ligaMapper = ligaMapper;
    }

    // GET /api/ligas → obtener todas las ligas
    @GetMapping
    public List<LigaResponseDTO> obtenerTodas() {
        return ligaService.obtenerTodas()
                .stream()
                .map(ligaMapper::toDTO)
                .toList();
    }

    // GET /api/ligas/{id} → obtener una liga por ID
    @GetMapping("/{id}")
    public ResponseEntity<LigaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ligaService.obtenerPorId(id)
                .map(ligaMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/ligas → crear una nueva liga
    @PostMapping
    public ResponseEntity<LigaResponseDTO> crear(@RequestBody LigaRequestDTO dto, @RequestParam Long usuarioId) {
        Liga liga = ligaMapper.toEntity(dto);
        Liga guardada = ligaService.guardar(liga, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ligaMapper.toDTO(guardada));
    }

    // GET /api/ligas/nombre/{nombre} → buscar por nombre exacto
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<LigaResponseDTO> buscarPorNombre(@PathVariable String nombre) {
        return ligaService.buscarPorNombre(nombre)
                .map(ligaMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/ligas/privadas?esPrivada=true|false → filtrar por privadas/públicas
    @GetMapping("/privadas")
    public List<LigaResponseDTO> obtenerPorPrivacidad(@RequestParam boolean esPrivada) {
        return ligaService.obtenerPorPrivacidad(esPrivada)
                .stream()
                .map(ligaMapper::toDTO)
                .toList();
    }

    // DELETE /api/ligas/{id} → eliminar una liga por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (ligaService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ligaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // POST /api/ligas/{id}/unirse → unirse a una liga por su ID
    @PostMapping("/{id}/unirse")
    public ResponseEntity<EquipoUsuarioResponseDTO> unirseALiga(
            @PathVariable Long id,
            @RequestBody UnirseLigaRequestDTO request
    ) {
        EquipoUsuarioResponseDTO equipoDto = ligaService.unirseALiga(id, request.getUsuarioId());
        return ResponseEntity.ok(equipoDto);
    }

    // POST /api/ligas/unirsePrivada → unirse a una liga privada usando nombre y clave
    @PostMapping("/unirsePrivada")
    public ResponseEntity<EquipoUsuarioResponseDTO> unirseALigaPrivada(
            @RequestBody UnirseLigaPrivadaRequestDTO request
    ) {
        EquipoUsuarioResponseDTO equipoDto = ligaService.unirseALigaPrivada(
                request.getNombreLiga(),
                request.getClaveAcceso(),
                request.getUsuarioId()
        );
        return ResponseEntity.ok(equipoDto);
    }


    // GET /api/ligas/privadas/{id} → obtener ligas privadas del usuario
    @GetMapping("/privadas/{id}")
    public ResponseEntity<List<LigaResponseDTO>> obtenerLigasPrivadasUsuario(@PathVariable Long id) {
        List<Liga> ligas = usuarioService.obtenerLigasPrivadas(id);
        List<LigaResponseDTO> dtos = ligas.stream().map(ligaMapper::toDTO).toList();
        return ResponseEntity.ok(dtos);
    }



}
