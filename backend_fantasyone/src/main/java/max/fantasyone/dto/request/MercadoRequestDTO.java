package max.fantasyone.dto.request;

import java.time.LocalDate;
import java.util.List;

public class MercadoRequestDTO {
    private LocalDate fecha;
    private List<Long> pilotosDia;

    public MercadoRequestDTO() {}

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

