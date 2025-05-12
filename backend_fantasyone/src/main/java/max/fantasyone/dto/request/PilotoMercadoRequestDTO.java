package max.fantasyone.dto.request;


public class PilotoMercadoRequestDTO {
    private Long pilotoId;
    private Long mercadoId;
    private boolean fichado;

    // Getters y setters
    public Long getPilotoId() {
        return pilotoId;
    }

    public void setPilotoId(Long pilotoId) {
        this.pilotoId = pilotoId;
    }

    public Long getMercadoId() {
        return mercadoId;
    }

    public void setMercadoId(Long mercadoId) {
        this.mercadoId = mercadoId;
    }

    public boolean isFichado() {
        return fichado;
    }

    public void setFichado(boolean fichado) {
        this.fichado = fichado;
    }
}
