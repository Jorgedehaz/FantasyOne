package max.fantasyone.mapper;


import max.fantasyone.dto.request.ApuestaVirtualRequestDTO;
import max.fantasyone.dto.response.ApuestaVirtualResponseDTO;
import max.fantasyone.model.*;
import max.fantasyone.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApuestaVirtualMapper {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private LigaRepository ligaRepository;
    @Autowired private CarreraRepository carreraRepository;
    @Autowired private PilotoRepository pilotoRepository;

    public ApuestaVirtual toEntity(ApuestaVirtualRequestDTO dto) {
        ApuestaVirtual apuesta = new ApuestaVirtual();
        apuesta.setUsuario(usuarioRepository.findById(dto.getUsuarioId()).orElseThrow());
        apuesta.setLiga(ligaRepository.findById(dto.getLigaId()).orElseThrow());
        apuesta.setCarrera(carreraRepository.findById(dto.getCarreraId()).orElseThrow());
        apuesta.setPiloto(pilotoRepository.findById(dto.getPilotoId()).orElseThrow());
        apuesta.setTipo(dto.getTipo());
        apuesta.setApostado(dto.getApostado());
        apuesta.setAcertada(dto.isAcertada());
        apuesta.setGanado(dto.getGanado());
        apuesta.setFecha(dto.getFecha());
        return apuesta;
    }

    public ApuestaVirtualResponseDTO toDTO(ApuestaVirtual a) {
        return new ApuestaVirtualResponseDTO(
                a.getId(),
                a.getUsuario().getNombre(),
                a.getLiga().getNombre(),
                a.getCarrera().getNombreGP(),
                a.getPiloto().getNombreCompleto(),
                a.getTipo(),
                a.getApostado(),
                a.isAcertada(),
                a.getGanado(),
                a.getFecha()
        );
    }
}
