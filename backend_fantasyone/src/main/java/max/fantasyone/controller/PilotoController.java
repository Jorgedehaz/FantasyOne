package max.fantasyone.controller;

import max.fantasyone.dto.request.PilotoRequestDTO;
import max.fantasyone.dto.response.PilotoResponseDTO;
import max.fantasyone.mapper.PilotoMapper;
import max.fantasyone.model.Piloto;
import max.fantasyone.service.PilotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pilotos")
public class PilotoController {

    private final PilotoService pilotoService;
    private final PilotoMapper pilotoMapper;

    @Autowired
    public PilotoController(PilotoService pilotoService, PilotoMapper pilotoMapper) {
        this.pilotoService = pilotoService;
        this.pilotoMapper = pilotoMapper;
    }

    // GET /api/pilotos → listar todos con DTO
    @GetMapping
    public List<PilotoResponseDTO> obtenerTodos() {
        return pilotoService.obtenerTodos()
                .stream()
                .map(pilotoMapper::toDTO)
                .toList();
    }

    // GET /api/pilotos/nofichados → para el mercado
    @GetMapping("/nofichados")
    public List<PilotoResponseDTO> obtenerNoFichados() {
        return pilotoService.obtenerPilotosNoFichados()
                .stream()
                .map(pilotoMapper::toDTO)
                .toList();
    }

    // POST /api/pilotos → crear nuevo piloto
    @PostMapping
    public ResponseEntity<PilotoResponseDTO> crear(@RequestBody PilotoRequestDTO dto) {
        Piloto piloto = pilotoMapper.toEntity(dto);
        Piloto guardado = pilotoService.guardar(piloto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pilotoMapper.toDTO(guardado));
    }

    // POST /api/pilotos/lote → crea varios pilotos a la vez desde una lista (futuro endpoit para beber de la API openf1.org)
    @PostMapping("/lote")
    public ResponseEntity<?> crearPilotosLote(@RequestBody List<PilotoRequestDTO> pilotosDto) {
        for (PilotoRequestDTO dto : pilotosDto) {
            pilotoService.guardar(dto);
        }
        return ResponseEntity.ok("Pilotos creados correctamente.");
    }


    // GET /api/pilotos/{id} → consultar piloto por ID
    @GetMapping("/{id}")
    public ResponseEntity<PilotoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return pilotoService.obtenerPorId(id)
                .map(pilotoMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/pilotos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pilotoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
