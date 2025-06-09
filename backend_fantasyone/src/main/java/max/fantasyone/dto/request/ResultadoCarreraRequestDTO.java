package max.fantasyone.dto.request;

public class ResultadoCarreraRequestDTO {
    private String pilotoExternalId;
    private Long carreraId;
    private int posicionFinal;
    private boolean vueltaRapida;
    private boolean polePosition;
    private boolean penalizado;
    private String tiempoTotal;
    private int paradasBoxes;
    private int stints;
    private String tipoNeumatico;
    private int puntosFantasy;

    public ResultadoCarreraRequestDTO() {}

    public String getPilotoExternalId() {return pilotoExternalId;}

    public void setPilotoExternalId(String pilotoExternalId) {this.pilotoExternalId = pilotoExternalId;}

    public Long getCarreraId() {
        return carreraId;
    }

    public void setCarreraId(Long carreraId) {
        this.carreraId = carreraId;
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
