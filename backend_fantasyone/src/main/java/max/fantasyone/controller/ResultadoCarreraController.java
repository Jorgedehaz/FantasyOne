package max.fantasyone.controller;

import max.fantasyone.mapper.ResultadoCarreraMapper;
import max.fantasyone.service.ResultadoInitializeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import max.fantasyone.dto.response.ResultadoCarreraResponseDTO;
import max.fantasyone.model.ResultadoCarrera;
import max.fantasyone.service.ResultadoCarreraService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/resultados")
public class ResultadoCarreraController {

    private final ResultadoCarreraService resultadoService;
    private final ResultadoInitializeService resultadoInitializeService;
    private final ResultadoCarreraMapper mapper;

    @Autowired
    public ResultadoCarreraController(ResultadoCarreraService resultadoService, ResultadoInitializeService resultadoInitializeService,ResultadoCarreraMapper mapper) {
        this.resultadoService = resultadoService;
        this.resultadoInitializeService = resultadoInitializeService;
        this.mapper = mapper;
    }

    // GET api/rsultados/recalcular-puntos -> Calcula los puntos para todos de forma manual (para pruebas o ajustes)
    @PostMapping("/recalcular-puntos")
    public ResponseEntity<Void> recalcularPuntosManualmente() {
        resultadoService.calcularPuntosParaTodos();
        return ResponseEntity.ok().build();
    }

    // GET api/rsultados/recalcular-ultimospuntos -> Calcula los puntos del ultimo resultado para todos
    @PostMapping("/recalcular-ultimospuntos")
    public ResponseEntity<Void> recalcular() {
        resultadoService.recalcularPuntosUltimoResultado();
        return ResponseEntity.ok().build();
    }

    //GET Devuelve la lista completa de resultados de carrera
    @GetMapping
    public ResponseEntity<List<ResultadoCarreraResponseDTO>> getAllResultados() {
        List<ResultadoCarrera> resultados = resultadoService.findAll();
        List<ResultadoCarreraResponseDTO> dtos = resultados.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // GET api/resultados/carrera/id -> Obtiene el histórico de resultados para una carrera concreta
    @GetMapping("/carrera/{carreraId}")
    public ResponseEntity<List<ResultadoCarreraResponseDTO>> getResultadosByCarrera(
            @PathVariable Long carreraId) {
        List<ResultadoCarreraResponseDTO> dtos = resultadoService.obtenerResultadosPorCarrera(carreraId);
        return ResponseEntity.ok(dtos);
    }

    // POST api/rsultados/initialize -> Inicializa o recarga los resultados desde un archivo JSON de ejemplo
    @PostMapping("/initialize")
    public ResponseEntity<Void> initializeResultados() throws Exception {
        resultadoInitializeService.importarDesdeJson();
        return ResponseEntity.ok().build();
    }


}
