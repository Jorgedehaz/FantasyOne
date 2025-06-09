package max.fantasyone.controller;

import max.fantasyone.dto.request.MercadoRequestDTO;
import max.fantasyone.dto.response.MercadoResponseDTO;
import max.fantasyone.dto.response.PilotoResponseDTO;
import max.fantasyone.mapper.MercadoMapper;
import max.fantasyone.mapper.PilotoMapper;
import max.fantasyone.model.Mercado;
import max.fantasyone.model.Piloto;
import max.fantasyone.service.MercadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mercados")
public class MercadoController {

    private final MercadoService mercadoService;
    private final MercadoMapper mercadoMapper;
    private final PilotoMapper pilotoMapper;  // inyectamos el mapper

    @Autowired
    public MercadoController(MercadoService mercadoService,
                             MercadoMapper mercadoMapper,
                             PilotoMapper pilotoMapper) {
        this.mercadoService = mercadoService;
        this.mercadoMapper = mercadoMapper;
        this.pilotoMapper = pilotoMapper;
    }

    // GET /api/mercado → obtener todos los mercados
    @GetMapping
    public List<MercadoResponseDTO> obtenerTodos() {
        return mercadoService.obtenerTodos()
                .stream()
                .map(mercadoMapper::toDTO)
                .toList();
    }

    // GET /api/mercado/{id} → obtener mercado por ID
    @GetMapping("/{id}")
    public ResponseEntity<MercadoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return mercadoService.obtenerPorId(id)
                .map(mercadoMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/mercado/fecha/{fecha} → buscar mercado por fecha
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<MercadoResponseDTO> obtenerPorFecha(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return mercadoService.buscarPorFecha(fecha)
                .map(mercadoMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/mercado → crear nuevo mercado
    @PostMapping
    public ResponseEntity<MercadoResponseDTO> crear(@RequestBody MercadoRequestDTO dto) {
        if (mercadoService.buscarPorFecha(dto.getFecha()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Mercado mercado = mercadoMapper.toEntity(dto);
        Mercado creado = mercadoService.guardar(mercado);
        return ResponseEntity.status(HttpStatus.CREATED).body(mercadoMapper.toDTO(creado));
    }

    // DELETE /api/mercado/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (mercadoService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        mercadoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


    // GET /api/mercado/liga/{ligaId} → obtener el mercado de una liga por su ID
    @GetMapping("/liga/{ligaId}")
    public ResponseEntity<MercadoResponseDTO> obtenerMercadoPorLiga(@PathVariable Long ligaId) {
        Optional<Mercado> mercadoOpt = mercadoService.obtenerPorLigaId(ligaId);

        if (mercadoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        MercadoResponseDTO dto = mercadoMapper.toDTO(mercadoOpt.get());
        return ResponseEntity.ok(dto);
    }


    //GET /api/mercados/liga/{ligaId}/rotacion?limit=10
    //Devuelve hasta `limit` pilotos aleatorios para fichar.
    @GetMapping("/liga/{ligaId}/rotacion")
    public ResponseEntity<List<PilotoResponseDTO>> obtenerRotacion(
            @PathVariable Long ligaId,
            @RequestParam(defaultValue = "10") int limit) {

        List<Piloto> seleccionados = mercadoService
                .obtenerPilotosParaMercado(ligaId, limit);

        List<PilotoResponseDTO> dtoList = seleccionados.stream()
                .map(pilotoMapper::toDTO)
                .toList();

        return ResponseEntity.ok(dtoList);
    }

    // GET /api/mercado/liga/{ligaId}/pilotos → obtener pilotos de una liga
    @GetMapping("/liga/{ligaId}/pilotos")
    public ResponseEntity<List<Piloto>> obtenerPilotosPorLiga(@PathVariable Long ligaId) {
        List<Piloto> pilotos = mercadoService.obtenerPilotosPorLiga(ligaId);
        return ResponseEntity.ok(pilotos);
    }


}
