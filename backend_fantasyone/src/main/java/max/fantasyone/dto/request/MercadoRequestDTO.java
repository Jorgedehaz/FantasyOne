package max.fantasyone.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class MercadoRequestDTO {
    private LocalDate fecha;
    private List<Long> pilotosDia;
    @NotNull(message = "El ID de la liga es obligatorio")
    private Long ligaId;


    public MercadoRequestDTO() {}

    public Long getLigaId() {
        return ligaId;
    }

    public void setLigaId(Long ligaId) {
        this.ligaId = ligaId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public List<Long> getPilotosDia() {
        return pilotosDia;
    }

    public void setPilotosDia(List<Long> pilotosDia) {
        this.pilotosDia = pilotosDia;
    }
}

