package max.fantasyone.mapper;

import max.fantasyone.dto.request.MercadoRequestDTO;
import max.fantasyone.dto.response.MercadoResponseDTO;
import max.fantasyone.model.Liga;
import max.fantasyone.model.Mercado;
import max.fantasyone.model.Piloto;
import max.fantasyone.model.PilotoMercado;
import max.fantasyone.repository.LigaRepository;
import max.fantasyone.repository.PilotoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MercadoMapper {

    private final PilotoRepository pilotoRepository;
    private final LigaRepository ligaRepository;

    public MercadoMapper(PilotoRepository pilotoRepository, LigaRepository ligaRepository) {
        this.pilotoRepository = pilotoRepository;
        this.ligaRepository = ligaRepository;
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
        List<String> nombresPilotos = mercado.getPilotosMercado().stream()
                .map(pm -> pm.getPiloto().getNombreCompleto())
                .toList();

        return new MercadoResponseDTO(
                mercado.getId(),
                mercado.getFecha(),
                nombresPilotos,
                mercado.getLiga().getId()
        );
    }

}
