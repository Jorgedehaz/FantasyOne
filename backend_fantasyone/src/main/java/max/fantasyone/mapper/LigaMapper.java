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
        liga.setCodigoAcceso(dto.getCodigoAcceso() != null ? dto.getCodigoAcceso() : ""); // asigna "" si es null. Quiza no es lo mejor pero crear 2 entities liga o 2 DTO no me tenia sentido
        liga.setMaxUsuarios(dto.getMaxUsuarios());
        return liga;
    }

    public LigaResponseDTO toDTO(Liga liga) {
        LigaResponseDTO dto = new LigaResponseDTO();
        dto.setId(liga.getId());
        dto.setNombre(liga.getNombre());
        dto.setPrivada(liga.isPrivada());
        dto.setMaxUsuarios(liga.getMaxUsuarios());
        dto.setCodigoAcceso(liga.getCodigoAcceso() != null ? liga.getCodigoAcceso() : ""); // controlamos el null
        return dto;
    }

}

