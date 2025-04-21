package max.fantasyone.dto.request;

import java.time.LocalDate;

public class CarreraRequestDTO {
    private String nombreGP;
    private String circuito;
    private LocalDate fecha;
    private int temporada;
    private int meetingKey;

    public CarreraRequestDTO() {}

    public String getNombreGP() {
        return nombreGP;
    }

    public void setNombreGP(String nombreGP) {
        this.nombreGP = nombreGP;
    }

    public String getCircuito() {
        return circuito;
    }

    public void setCircuito(String circuito) {
        this.circuito = circuito;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getMeetingKey() {
        return meetingKey;
    }

    public void setMeetingKey(int meetingKey) {
        this.meetingKey = meetingKey;
    }

    public int getTemporada() {
        return temporada;
    }
    public void setTemporada(int temporada) {
        this.temporada = temporada;
    }
}

