package max.fantasyone.controller;

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

    @Autowired //sugerencia de ChatGPT. En las ultimas versiones de Spring la notacion va en el constructor (buenas practicas)
    public PilotoController(PilotoService pilotoService) {
        this.pilotoService = pilotoService;
    }

    // GET /api/pilotos → listar todos
    @GetMapping
    public List<Piloto> obtenerTodos() {
        return pilotoService.obtenerTodos();
    }

    // GET /api/pilotos/nofichados → para el mercado
    @GetMapping("/nofichados")
    public List<Piloto> obtenerNoFichados() {
        return pilotoService.obtenerPilotosNoFichados();
    }

    // POST /api/pilotos → crear nuevo piloto (para pruebas)
    @PostMapping
    public ResponseEntity<Piloto> crear(@RequestBody Piloto piloto) {
        Piloto guardado = pilotoService.guardar(piloto);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // GET /api/pilotos/{id} → consultar piloto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Piloto> obtenerPorId(@PathVariable Long id) {
        return pilotoService.obtenerPorId(id)
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
