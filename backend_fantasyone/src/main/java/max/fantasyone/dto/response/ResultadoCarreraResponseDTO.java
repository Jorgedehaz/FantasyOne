package max.fantasyone.dto.response;

import java.time.LocalDateTime;

public class ResultadoCarreraResponseDTO {
    private Long id;
    private String pilotoExternalId;
    private String carreraExternalId;
    private LocalDateTime momento;
    private int posicionFinal;
    private boolean vueltaRapida;
    private boolean polePosition;
    private boolean penalizado;
    private String tiempoTotal;
    private int paradasBoxes;
    private int stints;
    private String tipoNeumatico;
    private int puntosFantasy;

    public ResultadoCarreraResponseDTO(Long id, String pilotoExternalId, String carreraExternalId, LocalDateTime momento, boolean vueltaRapida, int posicionFinal, boolean polePosition, boolean penalizado, String tiempoTotal, int paradasBoxes, int stints, String tipoNeumatico, int puntosFantasy) {
        this.id = id;
        this.pilotoExternalId = pilotoExternalId;
        this.carreraExternalId = carreraExternalId;
        this.momento = momento;
        this.vueltaRapida = vueltaRapida;
        this.posicionFinal = posicionFinal;
        this.polePosition = polePosition;
        this.penalizado = penalizado;
        this.tiempoTotal = tiempoTotal;
        this.paradasBoxes = paradasBoxes;
        this.stints = stints;
        this.tipoNeumatico = tipoNeumatico;
        this.puntosFantasy = puntosFantasy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPilotoExternalId() {
        return pilotoExternalId;
    }

    public void setPilotoExternalId(String pilotoExternalId) {
        this.pilotoExternalId = pilotoExternalId;
    }

    public String getCarreraExternalId() {
        return carreraExternalId;
    }

    public void setCarreraExternalId(String carreraExternalId) {
        this.carreraExternalId = carreraExternalId;
    }

    public LocalDateTime getMomento() {
        return momento;
    }

    public void setMomento(LocalDateTime momento) {
        this.momento = momento;
    }

    public int getPosicionFinal() {
        return posicionFinal;
    }

    public void setPosicionFinal(int posicionFinal) {
        this.posicionFinal = posicionFinal;
    }

    public boolean isVueltaRapida() {
        return vueltaRapida;
    }

    public void setVueltaRapida(boolean vueltaRapida) {
        this.vueltaRapida = vueltaRapida;
    }

    public boolean isPolePosition() {
        return polePosition;
    }

    public void setPolePosition(boolean polePosition) {
        this.polePosition = polePosition;
    }

    public boolean isPenalizado() {
        return penalizado;
    }

    public void setPenalizado(boolean penalizado) {
        this.penalizado = penalizado;
    }

    public String getTiempoTotal() {
        return tiempoTotal;
    }

    public void setTiempoTotal(String tiempoTotal) {
        this.tiempoTotal = tiempoTotal;
    }

    public int getParadasBoxes() {
        return paradasBoxes;
    }

    public void setParadasBoxes(int paradasBoxes) {
        this.paradasBoxes = paradasBoxes;
    }

    public int getStints() {
        return stints;
    }

    public void setStints(int stints) {
        this.stints = stints;
    }

    public String getTipoNeumatico() {
        return tipoNeumatico;
    }

    public void setTipoNeumatico(String tipoNeumatico) {
        this.tipoNeumatico = tipoNeumatico;
    }

    public int getPuntosFantasy() {
        return puntosFantasy;
    }

    public void setPuntosFantasy(int puntosFantasy) {
        this.puntosFantasy = puntosFantasy;
    }
}