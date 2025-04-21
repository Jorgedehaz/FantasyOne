package max.fantasyone.mapper;

import max.fantasyone.dto.request.LigaRequestDTO;
import max.fantasyone.dto.response.LigaResponseDTO;
import max.fantasyone.model.Liga;
import org.springframework.stereotype.Component;

@Component
public class LigaMapper {

    public Liga toEntity(LigaRequestDTO dto) {
        Liga liga = new Liga();
        liga.setNombre(dto.getNombre());
        liga.setPrivada(dto.isPrivada());
        liga.setCodigoAcceso(dto.getCodigoAcceso());
        return liga;
    }

    public LigaResponseDTO toDTO(Liga liga) {
        return new LigaResponseDTO(liga.getId(), liga.getNombre(), liga.isPrivada());
    }
}

