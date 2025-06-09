package max.fantasyone.service;

import max.fantasyone.dto.request.EquipoUsuarioRequestDTO;
import max.fantasyone.mapper.EquipoUsuarioMapper;
import max.fantasyone.model.EquipoUsuario;
import max.fantasyone.model.Piloto;
import max.fantasyone.model.ResultadoCarrera;
import max.fantasyone.repository.EquipoUsuarioRepository;
import max.fantasyone.repository.PilotoRepository;
import max.fantasyone.repository.ResultadoCarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipoUsuarioService {

    private final EquipoUsuarioRepository equipoUsuarioRepository;
    private final PilotoRepository pilotoRepository;
    private final ResultadoCarreraRepository resultadoCarreraRepository;
    private final EquipoUsuarioMapper equipoUsuarioMapper;

    private static final double PRESUPUESTO_INICIAL = 200.0;

    @Autowired
    public EquipoUsuarioService(EquipoUsuarioRepository equipoUsuarioRepository,
                                PilotoRepository pilotoRepository,
                                ResultadoCarreraRepository resultadoCarreraRepository,
                                EquipoUsuarioMapper equipoUsuarioMapper) {
        this.equipoUsuarioRepository = equipoUsuarioRepository;
        this.pilotoRepository = pilotoRepository;
        this.resultadoCarreraRepository = resultadoCarreraRepository;
        this.equipoUsuarioMapper = equipoUsuarioMapper;
    }

    @Transactional
    public EquipoUsuario crearEquipo(EquipoUsuarioRequestDTO dto) {
        EquipoUsuario equipo = equipoUsuarioMapper.toEntity(dto);
        // Inicializar puntos y fecha de creación se maneja en la entidad

        // Fija el presupuesto inicial en la constante
        equipo.setMonedas(PRESUPUESTO_INICIAL);

        return equipoUsuarioRepository.save(equipo);
    }

    public List<EquipoUsuario> obtenerTodos() {
        return equipoUsuarioRepository.findAll();
    }

    public EquipoUsuario obtenerPorId(Long id) {
        return equipoUsuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado: " + id));
    }

    public void eliminar(Long id) {
        equipoUsuarioRepository.deleteById(id);
    }

    public List<EquipoUsuario> buscarPorUsuario(Long usuarioId) {
        return equipoUsuarioRepository.findByUsuario_Id(usuarioId);
    }

    public List<EquipoUsuario> buscarPorLiga(Long ligaId) {
        return equipoUsuarioRepository.findByLiga_Id(ligaId);
    }

    public EquipoUsuario buscarPorUsuarioYLiga(Long usuarioId, Long ligaId) {
        return equipoUsuarioRepository
                .findByUsuario_IdAndLiga_Id(usuarioId, ligaId)
                .orElse(null);
    }


    //Fichar un piloto en el equipo, actualiza presupuesto y lo marca como fichado=true para que no aparezca en el mercado
    @Transactional
    public EquipoUsuario ficharPiloto(Long equipoId, Long pilotoId) {
        EquipoUsuario equipo = obtenerPorId(equipoId);
        Piloto piloto = pilotoRepository.findById(pilotoId)
                .orElseThrow(() -> new IllegalArgumentException("Piloto no encontrado: " + pilotoId));

        // lógica de presupuesto
        if (equipo.getMonedas() < piloto.getPrecio()) {
            throw new IllegalStateException("No tienes suficiente presupuesto para fichar a " + piloto.getNombreCompleto());
        }

        if (equipo.getPilotos().size() >= 2) {
            throw new IllegalStateException("Máximo 2 pilotos por equipo");
        }

        // Marcar fecha de fichaje y persistirlo
        piloto.setFechaFichaje(LocalDateTime.now());
        // Persistir piloto antes de añadirlo al equipo
        pilotoRepository.save(piloto);

        // Añadirlo al equipo y restar presupuesto
        equipo.ficharPiloto(piloto);
        piloto.setFichado(true);

        // Guardar el cambio de 'fichado'
        pilotoRepository.save(piloto);

        // Recalcular puntos del equipo (solo a partir de la fecha de fichaje)
        actualizarPuntosEquipo(equipo.getId());

        return equipoUsuarioRepository.save(equipo);
    }


     // Recalcula y actualiza los puntos acumulados del equipo,sumando todos los resultados posteriores a la creación de este
     public void actualizarPuntosEquipo(Long equipoId) {
         EquipoUsuario equipo = obtenerPorId(equipoId);

         int puntosTotales = equipo.getPilotos().stream()
                 .mapToInt(p -> {
                     LocalDateTime fFicha = p.getFechaFichaje();
                     // sumo solo lo que este piloto ha hecho DESDE su fecha de fichaje
                     return resultadoCarreraRepository
                             .findByPilotoExternalIdAndMomentoAfter(p.getExternalId(), fFicha)
                             .stream()
                             .mapToInt(ResultadoCarrera::getPuntosFantasy)
                             .sum();
                 })
                 .sum();

         equipo.setPuntosAcumulados(puntosTotales);
         equipoUsuarioRepository.save(equipo);
     }


    //Obtiene la clasificación de una liga ordenada por puntos descendente
    @Transactional
    public List<EquipoUsuario> calcularClasificacion(Long ligaId) {
        List<EquipoUsuario> equipos = buscarPorLiga(ligaId);

        equipos.forEach(equipo -> {
            // usa exactamente la misma lógica que en actualizarPuntosEquipo(...)
            actualizarPuntosEquipo(equipo.getId());
        });

        // ordenar y devolver
        return equipos.stream()
                .sorted(Comparator.comparingInt(EquipoUsuario::getPuntosAcumulados).reversed())
                .toList();
    }
}