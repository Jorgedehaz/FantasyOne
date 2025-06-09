package max.fantasyone.controller;

import max.fantasyone.dto.request.CarreraRequestDTO;
import max.fantasyone.dto.response.CarreraResponseDTO;
import max.fantasyone.mapper.CarreraMapper;
import max.fantasyone.model.Carrera;
import max.fantasyone.service.CarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/carreras")
public class CarreraController {

    private final CarreraService carreraService;
    private final CarreraMapper carreraMapper;

    @Autowired
    public CarreraController(CarreraService carreraService, CarreraMapper carreraMapper) {
        this.carreraService = carreraService;
        this.carreraMapper = carreraMapper;
    }

    // GET /api/carreras → obtener todas las carreras
    @GetMapping
    public List<CarreraResponseDTO> obtenerTodas() {
        return carreraService.obtenerTodas()
                .stream()
                .map(carreraMapper::toDTO)
                .toList();
    }

    // GET /api/carreras/{id} → carrera por ID
    @GetMapping("/{id}")
    public ResponseEntity<CarreraResponseDTO> obtenerPorId(@PathVariable Long id) {
        return carreraService.obtenerPorId(id)
                .map(carreraMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/carreras/meeting/{key} → buscar por meetingKey
    @GetMapping("/meeting/{key}")
    public ResponseEntity<CarreraResponseDTO> obtenerPorMeetingKey(@PathVariable int key) {
        return carreraService.buscarPorMeetingKey(key)
                .map(carreraMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/carreras/nombre/{nombreGP} → buscar por nombre del GP
    @GetMapping("/nombre/{nombreGP}")
    public ResponseEntity<CarreraResponseDTO> obtenerPorNombreGP(@PathVariable String nombreGP) {
        return carreraService.buscarPorNombre(nombreGP)
                .map(carreraMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/carreras/fecha/{fecha} → buscar por fecha
    @GetMapping("/fecha/{fecha}")
    public List<CarreraResponseDTO> obtenerPorFecha(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return carreraService.buscarPorFecha(fecha)
                .stream()
                .map(carreraMapper::toDTO)
                .toList();
    }

    // POST /api/carreras → crear nueva carrera
    @PostMapping
    public ResponseEntity<CarreraResponseDTO> crear(@RequestBody CarreraRequestDTO dto) {
        Carrera carrera = carreraMapper.toEntity(dto);
        Carrera guardada = carreraService.guardar(carrera);
        return ResponseEntity.status(HttpStatus.CREATED).body(carreraMapper.toDTO(guardada));
    }

    // DELETE /api/carreras/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (carreraService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        carreraService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
