package max.fantasyone.controller;

import max.fantasyone.model.Mercado;
import max.fantasyone.service.MercadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/mercado")
public class MercadoController {

    private final MercadoService mercadoService;

    @Autowired
    public MercadoController(MercadoService mercadoService) {
        this.mercadoService = mercadoService;
    }

    // GET /api/mercado → obtener todos los mercados
    @GetMapping
    public List<Mercado> obtenerTodos() {
        return mercadoService.obtenerTodos();
    }

    // GET /api/mercado/{id} → obtener mercado por ID
    @GetMapping("/{id}")
    public ResponseEntity<Mercado> obtenerPorId(@PathVariable Long id) {
        return mercadoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/mercado/fecha/{fecha} → buscar mercado por fecha
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<Mercado> obtenerPorFecha(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return mercadoService.buscarPorFecha(fecha)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/mercado → crear un nuevo mercado
    @PostMapping
    public ResponseEntity<Mercado> crear(@RequestBody Mercado mercado) {
        // Evitar duplicados por fecha
        if (mercadoService.buscarPorFecha(mercado.getFecha()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        Mercado creado = mercadoService.guardar(mercado);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // DELETE /api/mercado/{id} → eliminar un mercado por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (mercadoService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        mercadoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

