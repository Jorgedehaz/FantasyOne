package max.fantasyone.mapper;

import max.fantasyone.dto.request.EquipoUsuarioRequestDTO;
import max.fantasyone.dto.response.EquipoUsuarioResponseDTO;
import max.fantasyone.dto.response.PilotoResponseDTO;
import max.fantasyone.model.EquipoUsuario;
import max.fantasyone.model.Piloto;
import max.fantasyone.repository.LigaRepository;
import max.fantasyone.repository.PilotoRepository;
import max.fantasyone.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EquipoUsuarioMapper {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private LigaRepository ligaRepository;
    @Autowired
    private PilotoRepository pilotoRepository;


    //Convierte un DTO de petición en la entidad EquipoUsuario.
    //No maneja lógica de negocio (fichajes), sólo asignaciones básicas.
    public EquipoUsuario toEntity(EquipoUsuarioRequestDTO dto) {
        EquipoUsuario equipo = new EquipoUsuario();
        equipo.setUsuario(
                usuarioRepository.findById(dto.getUsuarioId())
                        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"))
        );
        equipo.setLiga(
                ligaRepository.findById(dto.getLigaId())
                        .orElseThrow(() -> new IllegalArgumentException("Liga no encontrada"))
        );
        equipo.setMonedas(dto.getMonedas());

        // Mapear lista de IDs a entity Piloto
        List<Piloto> pilotos = dto.getPilotoIds().stream()
                .map(id -> pilotoRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Piloto no encontrado: " + id)))
                .collect(Collectors.toList());
        equipo.setPilotos(pilotos);
        return equipo;
    }


    //Convierte entity EquipoUsuario en DTOresponse de la entity.
    public EquipoUsuarioResponseDTO toDTO(EquipoUsuario equipo) {
        EquipoUsuarioResponseDTO dto = new EquipoUsuarioResponseDTO();
        dto.setId(equipo.getId());
        dto.setNombreUsuario(equipo.getUsuario().getNombre());
        dto.setNombreLiga(equipo.getLiga().getNombre());
        dto.setMonedas(equipo.getMonedas());
        dto.setPuntosAcumulados(equipo.getPuntosAcumulados());

        // Mapear lista de Piloto a PilotoResponseDTO
        List<PilotoResponseDTO> pilotosDto = equipo.getPilotos().stream()
                .map(p -> PilotoMapper.toDTO(p))
                .collect(Collectors.toList());
        dto.setPilotos(pilotosDto);

        return dto;
    }
}
