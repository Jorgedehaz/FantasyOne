package max.fantasyone.mapper;

import max.fantasyone.dto.request.MercadoRequestDTO;
import max.fantasyone.dto.response.MercadoResponseDTO;
import max.fantasyone.dto.response.PilotoResponseDTO;
import max.fantasyone.model.Liga;
import max.fantasyone.model.Mercado;
import max.fantasyone.repository.LigaRepository;
import max.fantasyone.repository.PilotoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MercadoMapper {

    private final PilotoRepository pilotoRepository;
    private final LigaRepository ligaRepository;
    private final PilotoMapper pilotoMapper;


    public MercadoMapper(PilotoRepository pilotoRepository, LigaRepository ligaRepository, PilotoMapper pilotoMapper) {
        this.pilotoRepository = pilotoRepository;
        this.ligaRepository = ligaRepository;
        this.pilotoMapper = pilotoMapper;
    }

    public Mercado toEntity(MercadoRequestDTO dto) {
        Mercado mercado = new Mercado();

        // Liga
        Liga liga = ligaRepository.findById(dto.getLigaId())
                .orElseThrow(() -> new IllegalArgumentException("Liga no encontrada con ID: " + dto.getLigaId()));
        mercado.setLiga(liga);

        return mercado;
    }

    public MercadoResponseDTO toDTO(Mercado mercado) {
        List<PilotoResponseDTO> pilotosDTO = mercado.getPilotosMercado().stream()
                // Primero filtramos aquellos pilotos que no estén fichados:
                .filter(p -> !p.isFichado())
                // Después los convertimos a PilotoResponseDTO usando su constructor:
                .map(p -> new PilotoResponseDTO(
                        p.getId(),
                        p.getNombreCompleto(),
                        p.getPrecio(),
                        p.getPuntosFantasy(),
                        p.getImagenUrl(),
                        p.getExternalId()
                ))
                .collect(Collectors.toList());

        return new MercadoResponseDTO(
                mercado.getId(),
                mercado.getFecha(),
                pilotosDTO,
                mercado.getLiga().getId()
        );
    }

}
