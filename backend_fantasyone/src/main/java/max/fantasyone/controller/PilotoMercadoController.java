package max.fantasyone.controller;

import max.fantasyone.dto.request.PilotoMercadoRequestDTO;
import max.fantasyone.dto.response.PilotoMercadoResponseDTO;
import max.fantasyone.service.PilotoMercadoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/piloto-mercado")
public class PilotoMercadoController {

    private final PilotoMercadoService service;

    public PilotoMercadoController(PilotoMercadoService service) {
        this.service = service;
    }

    // GET /api/piloto-mercado/mercado/{mercadoId} → obtener los pilotos disponibles de un mercado por su ID
    @GetMapping("/mercado/{mercadoId}")
    public List<PilotoMercadoResponseDTO> obtenerPorMercado(@PathVariable Long mercadoId) {
        return service.obtenerPorMercado(mercadoId);
    }

    // POST /api/piloto-mercado → asignar pilotos a un mercado (requiere id del mercado y lista de pilotos)
    @PostMapping
    public PilotoMercadoResponseDTO crear(@RequestBody PilotoMercadoRequestDTO dto) {
        return service.crear(dto);
    }
}
