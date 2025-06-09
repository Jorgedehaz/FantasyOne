package max.fantasyone.dto.request;

import max.fantasyone.model.TipoApuesta;
import java.time.LocalDateTime;

public class ApuestaVirtualRequestDTO {
    private Long usuarioId;
    private Long ligaId;
    private Long carreraId;
    private Long pilotoId;
    private TipoApuesta tipo;
    private double apostado;
    private boolean acertada;
    private double ganado;
    private LocalDateTime fecha;

    public ApuestaVirtualRequestDTO() {}

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

    public Long getCarreraId() {
        return carreraId;
    }

    public void setCarreraId(Long carreraId) {
        this.carreraId = carreraId;
    }

    public Long getPilotoId() {
        return pilotoId;
    }

    public void setPilotoId(Long pilotoId) {
        this.pilotoId = pilotoId;
    }

    public TipoApuesta getTipo() {
        return tipo;
    }

    public void setTipo(TipoApuesta tipo) {
        this.tipo = tipo;
    }

    public double getApostado() {
        return apostado;
    }

    public void setApostado(double apostado) {
        this.apostado = apostado;
    }

    public boolean isAcertada() {
        return acertada;
    }

    public void setAcertada(boolean acertada) {
        this.acertada = acertada;
    }

    public double getGanado() {
        return ganado;
    }

    public void setGanado(double ganado) {
        this.ganado = ganado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}

