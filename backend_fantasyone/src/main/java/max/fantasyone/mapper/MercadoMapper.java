package max.fantasyone.mapper;

import max.fantasyone.dto.request.MercadoRequestDTO;
import max.fantasyone.dto.response.MercadoResponseDTO;
import max.fantasyone.model.Liga;
import max.fantasyone.model.Mercado;
import max.fantasyone.model.Piloto;
import max.fantasyone.repository.LigaRepository;
import max.fantasyone.repository.PilotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MercadoMapper {

    @Autowired
    private PilotoRepository pilotoRepository;

    @Autowired
    private LigaRepository ligaRepository;

    public Mercado toEntity(MercadoRequestDTO dto) {
        if (dto.getFecha() == null || dto.getPilotosDia() == null || dto.getPilotosDia().isEmpty()) {
            throw new IllegalArgumentException("Fecha y lista de IDs de pilotos no pueden ser nulas o vacías.");
        }

        Mercado mercado = new Mercado();
        mercado.setFecha(dto.getFecha());

        // Buscar pilotos
        List<Piloto> pilotos = pilotoRepository.findAllById(dto.getPilotosDia());
        mercado.setPilotosDia(pilotos);

        // Buscar liga
        if (dto.getLigaId() == null) {
            throw new IllegalArgumentException("La ID de la liga no puede ser nula.");
        }

        Liga liga = ligaRepository.findById(dto.getLigaId())
                .orElseThrow(() -> new IllegalArgumentException("Liga no encontrada con ID: " + dto.getLigaId()));
        mercado.setLiga(liga);

        return mercado;
    }


    public MercadoResponseDTO toDTO(Mercado mercado) {
        List<String> nombresPilotos = mercado.getPilotosDia().stream()
                .map(Piloto::getNombreCompleto)
                .toList();

        return new MercadoResponseDTO(
                mercado.getId(),
                mercado.getFecha(),
                nombresPilotos,
                mercado.getLiga() != null ? mercado.getLiga().getId() : null
        );
    }

}

