package max.fantasyone.mapper;

import max.fantasyone.dto.request.MercadoRequestDTO;
import max.fantasyone.dto.response.MercadoResponseDTO;
import max.fantasyone.model.Mercado;
import max.fantasyone.model.Piloto;
import max.fantasyone.repository.PilotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MercadoMapper {

    @Autowired
    private PilotoRepository pilotoRepository;

    public Mercado toEntity(MercadoRequestDTO dto) {
        Mercado mercado = new Mercado();
        mercado.setFecha(dto.getFecha());
        List<Piloto> pilotos = pilotoRepository.findAllById(dto.getPilotosDia());
        mercado.setPilotosDia(pilotos);
        return mercado;
    }

    public MercadoResponseDTO toDTO(Mercado mercado) {
        List<String> nombres = mercado.getPilotosDia()
                .stream()
                .map(Piloto::getNombreCompleto)
                .toList();

        return new MercadoResponseDTO(
                mercado.getId(),
                mercado.getFecha(),
                nombres
        );
    }
}
