package max.fantasyone.controller;


import max.fantasyone.dto.request.ResultadoCarreraRequestDTO;
import max.fantasyone.dto.response.ResultadoCarreraResponseDTO;
import max.fantasyone.mapper.ResultadoCarreraMapper;
import max.fantasyone.model.ResultadoCarrera;
import max.fantasyone.service.ResultadoCarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resultados")
public class ResultadoCarreraController {

    private final ResultadoCarreraService resultadoService;
    private final ResultadoCarreraMapper resultadoMapper;

    @Autowired
    public ResultadoCarreraController(ResultadoCarreraService resultadoService,
                                      ResultadoCarreraMapper resultadoMapper) {
        this.resultadoService = resultadoService;
        this.resultadoMapper = resultadoMapper;
    }

    // GET /api/resultados
    @GetMapping
    public List<ResultadoCarreraResponseDTO> obtenerTodos() {
        return resultadoService.obtenerTodos()
                .stream()
                .map(resultadoMapper::toDTO)
                .toList();
    }

    // GET /api/resultados/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ResultadoCarreraResponseDTO> obtenerPorId(@PathVariable Long id) {
        return resultadoService.obtenerPorId(id)
                .map(resultadoMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/resultados/piloto/{pilotoId}
    @GetMapping("/piloto/{pilotoId}")
    public List<ResultadoCarreraResponseDTO> obtenerPorPiloto(@PathVariable Long pilotoId) {
        return resultadoService.buscarPorPiloto(pilotoId)
                .stream()
                .map(resultadoMapper::toDTO)
                .toList();
    }

    // GET /api/resultados/carrera/{carreraId}
    @GetMapping("/carrera/{carreraId}")
    public List<ResultadoCarreraResponseDTO> obtenerPorCarrera(@PathVariable Long carreraId) {
        return resultadoService.buscarPorCarrera(carreraId)
                .stream()
                .map(resultadoMapper::toDTO)
                .toList();
    }

    // POST /api/resultados
    @PostMapping
    public ResponseEntity<ResultadoCarreraResponseDTO> crear(@RequestBody ResultadoCarreraRequestDTO dto) {
        ResultadoCarrera resultado = resultadoMapper.toEntity(dto);
        ResultadoCarrera guardado = resultadoService.guardar(resultado);
        return ResponseEntity.status(201).body(resultadoMapper.toDTO(guardado));
    }

    // DELETE /api/resultados/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (resultadoService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        resultadoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

