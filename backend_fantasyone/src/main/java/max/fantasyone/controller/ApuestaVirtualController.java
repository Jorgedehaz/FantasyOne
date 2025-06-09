package max.fantasyone.controller;

import max.fantasyone.dto.request.ApuestaVirtualRequestDTO;
import max.fantasyone.dto.response.ApuestaVirtualResponseDTO;
import max.fantasyone.mapper.ApuestaVirtualMapper;
import max.fantasyone.model.ApuestaVirtual;
import max.fantasyone.service.ApuestaVirtualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/apuestas")
public class ApuestaVirtualController {

    private final ApuestaVirtualService apuestaService;
    private final ApuestaVirtualMapper apuestaMapper;

    @Autowired
    public ApuestaVirtualController(ApuestaVirtualService apuestaService,
                                    ApuestaVirtualMapper apuestaMapper) {
        this.apuestaService = apuestaService;
        this.apuestaMapper = apuestaMapper;
    }

    // GET /api/apuestas → obtener todas
    @GetMapping
    public List<ApuestaVirtualResponseDTO> obtenerTodas() {
        return apuestaService.obtenerTodas()
                .stream()
                .map(apuestaMapper::toDTO)
                .toList();
    }

    // GET /api/apuestas/usuario/{id}
    @GetMapping("/usuario/{usuarioId}")
    public List<ApuestaVirtualResponseDTO> buscarPorUsuario(@PathVariable Long usuarioId) {
        return apuestaService.buscarPorUsuario(usuarioId)
                .stream()
                .map(apuestaMapper::toDTO)
                .toList();
    }

    // GET /api/apuestas/liga/{id}
    @GetMapping("/liga/{ligaId}")
    public List<ApuestaVirtualResponseDTO> buscarPorLiga(@PathVariable Long ligaId) {
        return apuestaService.buscarPorLiga(ligaId)
                .stream()
                .map(apuestaMapper::toDTO)
                .toList();
    }

    // GET /api/apuestas/carrera/{id}
    @GetMapping("/carrera/{carreraId}")
    public List<ApuestaVirtualResponseDTO> buscarPorCarrera(@PathVariable Long carreraId) {
        return apuestaService.buscarPorCarrera(carreraId)
                .stream()
                .map(apuestaMapper::toDTO)
                .toList();
    }

    // GET /api/apuestas/piloto/{id}
    @GetMapping("/piloto/{pilotoId}")
    public List<ApuestaVirtualResponseDTO> buscarPorPiloto(@PathVariable Long pilotoId) {
        return apuestaService.buscarPorPiloto(pilotoId)
                .stream()
                .map(apuestaMapper::toDTO)
                .toList();
    }

    // POST /api/apuestas
    @PostMapping
    public ResponseEntity<ApuestaVirtualResponseDTO> crear(@RequestBody ApuestaVirtualRequestDTO dto) {
        ApuestaVirtual apuesta = apuestaMapper.toEntity(dto);
        ApuestaVirtual guardada = apuestaService.guardar(apuesta);
        return ResponseEntity.status(HttpStatus.CREATED).body(apuestaMapper.toDTO(guardada));
    }

    // PUT /api/apuestas/{id}/acierto
    @PutMapping("/{id}/acierto")
    public ResponseEntity<ApuestaVirtualResponseDTO> actualizarResultado(
            @PathVariable Long id,
            @RequestParam boolean acertada,
            @RequestParam double ganado
    ) {
        Optional<ApuestaVirtual> apuestaOpt = apuestaService.obtenerPorId(id);
        if (apuestaOpt.isEmpty()) return ResponseEntity.notFound().build();

        ApuestaVirtual apuesta = apuestaOpt.get();
        apuesta.setAcertada(acertada);
        apuesta.setGanado(ganado);
        ApuestaVirtual actualizada = apuestaService.guardar(apuesta);

        return ResponseEntity.ok(apuestaMapper.toDTO(actualizada));
    }

    // DELETE /api/apuestas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (apuestaService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        apuestaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
