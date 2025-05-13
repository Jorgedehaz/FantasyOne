package max.fantasyone.dto.response;

import java.time.LocalDate;
import java.util.List;

public class MercadoResponseDTO {
    private final Long id;
    private Long ligaId;
    private final LocalDate fecha;
    private final List<PilotoResponseDTO> pilotos;

    public MercadoResponseDTO(Long id, LocalDate fecha, List<PilotoResponseDTO> pilotos, Long ligaId) {
        this.id = id;
        this.fecha = fecha;
        this.pilotos = pilotos;
        this.ligaId = ligaId;
    }

    public Long getId() {
        return id;
    }

    public Long getLigaId() {
        return ligaId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public List<PilotoResponseDTO> getPilotos() {
        return pilotos;
    }
}
