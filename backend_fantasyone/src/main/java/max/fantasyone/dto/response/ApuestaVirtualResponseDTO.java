package max.fantasyone.dto.response;

import max.fantasyone.model.TipoApuesta;

import java.time.LocalDateTime;

public class ApuestaVirtualResponseDTO {
    private final Long id;
    private final String usuario;
    private final String liga;
    private final String carrera;
    private final String piloto;
    private final TipoApuesta tipo;
    private final double apostado;
    private final boolean acertada;
    private final double ganado;
    private final LocalDateTime fecha;

    public ApuestaVirtualResponseDTO(Long id, String usuario, String liga, String carrera,
                                     String piloto, TipoApuesta tipo, double apostado,
                                     boolean acertada, double ganado, LocalDateTime fecha) {
        this.id = id;
        this.usuario = usuario;
        this.liga = liga;
        this.carrera = carrera;
        this.piloto = piloto;
        this.tipo = tipo;
        this.apostado = apostado;
        this.acertada = acertada;
        this.ganado = ganado;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getLiga() {
        return liga;
    }

    public String getCarrera() {
        return carrera;
    }

    public String getPiloto() {
        return piloto;
    }

    public TipoApuesta getTipo() {
        return tipo;
    }

    public double getApostado() {
        return apostado;
    }

    public boolean isAcertada() {
        return acertada;
    }

    public double getGanado() {
        return ganado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}
