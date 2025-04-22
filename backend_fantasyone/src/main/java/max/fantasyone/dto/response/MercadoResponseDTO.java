package max.fantasyone.dto.response;

import java.time.LocalDate;
import java.util.List;

public class MercadoResponseDTO {
    private final Long id;
    private Long ligaId;
    private final LocalDate fecha;
    private final List<String> nombresPilotos;

    public MercadoResponseDTO(Long id, LocalDate fecha, List<String> nombresPilotos, Long ligaId) {
        this.id = id;
        this.fecha = fecha;
        this.nombresPilotos = nombresPilotos;
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

    public List<String> getNombresPilotos() {
        return nombresPilotos;
    }
}
