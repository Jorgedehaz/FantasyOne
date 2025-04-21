package max.fantasyone.dto.response;

import java.time.LocalDate;

public class CarreraResponseDTO {
    private final Long id;
    private final String nombreGP;
    private final String circuito;
    private final LocalDate fecha;
    private final int meetingKey;
    private int temporada;

    public CarreraResponseDTO(Long id, String nombreGP, String circuito, LocalDate fecha, int meetingKey, int temporada) {
        this.id = id;
        this.nombreGP = nombreGP;
        this.circuito = circuito;
        this.fecha = fecha;
        this.meetingKey = meetingKey;
        this.temporada = temporada;
    }

    public Long getId() {
        return id;
    }

    public String getNombreGP() {
        return nombreGP;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getCircuito() {
        return circuito;
    }

    public int getMeetingKey() {
        return meetingKey;
    }

    public int getTemporada() {
        return temporada;
    }

}
