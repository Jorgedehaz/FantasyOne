package max.fantasyone.dto.response;

public class PilotoMercadoResponseDTO {
    private Long id;
    private Long pilotoId;
    private String nombrePiloto;
    private Long mercadoId;
    private boolean fichado;
    private double precio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPilotoId() {
        return pilotoId;
    }

    public void setPilotoId(Long pilotoId) {
        this.pilotoId = pilotoId;
    }

    public String getNombrePiloto() {
        return nombrePiloto;
    }

    public void setNombrePiloto(String nombrePiloto) {
        this.nombrePiloto = nombrePiloto;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
