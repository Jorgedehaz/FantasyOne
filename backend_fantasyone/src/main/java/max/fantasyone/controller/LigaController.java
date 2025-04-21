package max.fantasyone.controller;

import max.fantasyone.model.Liga;
import max.fantasyone.service.LigaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ligas")
public class LigaController {

    private final LigaService ligaService;

    @Autowired
    public LigaController(LigaService ligaService) {
        this.ligaService = ligaService;
    }

    // GET /api/ligas → obtener todas las ligas
    @GetMapping
    public List<Liga> obtenerTodas() {
        return ligaService.obtenerTodas();
    }

    // GET /api/ligas/{id} → obtener una liga por ID
    @GetMapping("/{id}")
    public ResponseEntity<Liga> obtenerPorId(@PathVariable Long id) {
        return ligaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/ligas → crear una nueva liga
    @PostMapping
    public ResponseEntity<Liga> crear(@RequestBody Liga liga) {
        Liga guardada = ligaService.guardar(liga);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    // GET /api/ligas/nombre/{nombre} → buscar por nombre exacto
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Liga> buscarPorNombre(@PathVariable String nombre) {
        return ligaService.buscarPorNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/ligas/privadas?esPrivada=true|false → filtrar por si es privada o pública
    @GetMapping("/privadas")
    public List<Liga> obtenerPorPrivacidad(@RequestParam boolean esPrivada) {
        return ligaService.obtenerPorPrivacidad(esPrivada);
    }

    // DELETE /api/ligas/{id} → eliminar una liga por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (ligaService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ligaService.eliminar(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
