package max.fantasyone.dto.response;

public class ResultadoCarreraResponseDTO {
    private final Long id;
    private final String nombrePiloto;
    private final String nombreGP;
    private final int posicionFinal;
    private final boolean vueltaRapida;
    private final boolean polePosition;
    private final boolean penalizado;
    private final String tiempoTotal;
    private final int paradasBoxes;
    private final int stints;
    private final String tipoNeumatico;
    private final int puntosFantasy;

    public ResultadoCarreraResponseDTO(Long id, String nombrePiloto, String nombreGP, int posicionFinal,
                                       boolean vueltaRapida, boolean polePosition, boolean penalizado,
                                       String tiempoTotal, int paradasBoxes, int stints,
                                       String tipoNeumatico, int puntosFantasy) {
        this.id = id;
        this.nombrePiloto = nombrePiloto;
        this.nombreGP = nombreGP;
        this.posicionFinal = posicionFinal;
        this.vueltaRapida = vueltaRapida;
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

    public String getNombrePiloto() {
        return nombrePiloto;
    }

    public String getNombreGP() {
        return nombreGP;
    }

    public int getPosicionFinal() {
        return posicionFinal;
    }

    public boolean isVueltaRapida() {
        return vueltaRapida;
    }

    public boolean isPolePosition() {
        return polePosition;
    }

    public boolean isPenalizado() {
        return penalizado;
    }

    public String getTiempoTotal() {
        return tiempoTotal;
    }

    public int getParadasBoxes() {
        return paradasBoxes;
    }

    public int getStints() {
        return stints;
    }

    public String getTipoNeumatico() {
        return tipoNeumatico;
    }

    public int getPuntosFantasy() {
        return puntosFantasy;
    }
}
