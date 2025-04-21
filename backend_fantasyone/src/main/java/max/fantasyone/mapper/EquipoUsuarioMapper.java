package max.fantasyone.mapper;

import max.fantasyone.dto.request.EquipoUsuarioRequestDTO;
import max.fantasyone.dto.response.EquipoUsuarioResponseDTO;
import max.fantasyone.model.EquipoUsuario;
import max.fantasyone.repository.LigaRepository;
import max.fantasyone.repository.PilotoRepository;
import max.fantasyone.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EquipoUsuarioMapper {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private LigaRepository ligaRepository;
    @Autowired private PilotoRepository pilotoRepository;

    public EquipoUsuario toEntity(EquipoUsuarioRequestDTO dto) {
        EquipoUsuario equipo = new EquipoUsuario();
        equipo.setUsuario(usuarioRepository.findById(dto.getUsuarioId()).orElseThrow());
        equipo.setLiga(ligaRepository.findById(dto.getLigaId()).orElseThrow());
        equipo.setPiloto1(pilotoRepository.findById(dto.getPiloto1Id()).orElseThrow());
        equipo.setPiloto2(pilotoRepository.findById(dto.getPiloto2Id()).orElseThrow());
        equipo.setMonedas(dto.getMonedas());
        equipo.setPuntosAcumulados(dto.getPuntosAcumulados());
        return equipo;
    }

    public EquipoUsuarioResponseDTO toDTO(EquipoUsuario equipo) {
        return new EquipoUsuarioResponseDTO(
                equipo.getId(),
                equipo.getUsuario().getNombre(),
                equipo.getLiga().getNombre(),
                equipo.getPiloto1().getNombreCompleto(),
                equipo.getPiloto2().getNombreCompleto(),
                equipo.getMonedas(),
                equipo.getPuntosAcumulados()
        );
    }
}
