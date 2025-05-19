package max.fantasyone.dto.request;


import jakarta.validation.constraints.Size;
import java.util.List;

public class EquipoUsuarioRequestDTO {
    private Long usuarioId;
    private Long ligaId;

    @Size(max = 2, message = "Un equipo no puede tener más de 2 pilotos")
    private List<Long> pilotoIds;

    private double monedas;

    public EquipoUsuarioRequestDTO() {}

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getLigaId() {
        return ligaId;
    }

    public void setLigaId(Long ligaId) {
        this.ligaId = ligaId;
    }

    public List<Long> getPilotoIds() {
        return pilotoIds;
    }

    public void setPilotoIds(List<Long> pilotoIds) {
        this.pilotoIds = pilotoIds;
    }

    public double getMonedas() {
        return monedas;
    }

    public void setMonedas(double monedas) {
        this.monedas = monedas;
    }
}

