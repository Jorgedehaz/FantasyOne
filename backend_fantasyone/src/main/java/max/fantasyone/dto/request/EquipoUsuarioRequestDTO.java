package max.fantasyone.dto.request;


public class EquipoUsuarioRequestDTO {
    private Long usuarioId;
    private Long ligaId;
    private Long piloto1Id;
    private Long piloto2Id;
    private double monedas;
    private int puntosAcumulados;

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

    public Long getPiloto1Id() {
        return piloto1Id;
    }

    public void setPiloto1Id(Long piloto1Id) {
        this.piloto1Id = piloto1Id;
    }

    public Long getPiloto2Id() {
        return piloto2Id;
    }

    public void setPiloto2Id(Long piloto2Id) {
        this.piloto2Id = piloto2Id;
    }

    public double getMonedas() {
        return monedas;
    }

    public void setMonedas(double monedas) {
        this.monedas = monedas;
    }

    public int getPuntosAcumulados() {
        return puntosAcumulados;
    }

    public void setPuntosAcumulados(int puntosAcumulados) {
        this.puntosAcumulados = puntosAcumulados;
    }
}

