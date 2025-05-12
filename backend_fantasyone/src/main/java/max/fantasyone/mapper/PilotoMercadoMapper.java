package max.fantasyone.mapper;

import max.fantasyone.dto.request.PilotoMercadoRequestDTO;
import max.fantasyone.dto.response.PilotoMercadoResponseDTO;
import max.fantasyone.model.PilotoMercado;

public class PilotoMercadoMapper {

    public static PilotoMercadoResponseDTO toDTO(PilotoMercado entity) {
        PilotoMercadoResponseDTO dto = new PilotoMercadoResponseDTO();
        dto.setId(entity.getId());
        dto.setPilotoId(entity.getPiloto().getId());
        dto.setNombrePiloto(entity.getPiloto().getNombre());
        dto.setMercadoId(entity.getMercado().getId());
        dto.setFichado(entity.isFichado());
        dto.setPrecio(entity.getPiloto().getPrecio());
        return dto;
    }

    public static PilotoMercado toEntity(PilotoMercadoRequestDTO dto) {
        PilotoMercado entity = new PilotoMercado();
        // Carga de piloto y mercado en el servicio
        entity.setFichado(dto.isFichado());
        return entity;
    }
}

