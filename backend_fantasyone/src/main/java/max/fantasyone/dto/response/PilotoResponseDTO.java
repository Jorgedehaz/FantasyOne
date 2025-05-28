package max.fantasyone.dto.response;

import max.fantasyone.model.Piloto;

public class PilotoResponseDTO {

    private Long id;
    private String nombreCompleto;
    private String equipo;
    private String imagenUrl;
    private double precio;
    private boolean fichado;
    private int puntosFantasy;
    private String pais;
    private int numero;
    private String pilotoExternalId;

    public PilotoResponseDTO(Piloto piloto) {
        this.id = piloto.getId();
        this.nombreCompleto = piloto.getNombreCompleto();
        this.equipo = piloto.getEquipo();
        this.imagenUrl = piloto.getImagenUrl();
        this.precio = piloto.getPrecio();
        this.fichado = piloto.isFichado();
        this.puntosFantasy = piloto.getPuntosFantasy();
        this.pais = piloto.getPais();
        this.numero = piloto.getNumero();
        this.pilotoExternalId = piloto.getExternalId();
    }

    public Long getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getEquipo() {
        return equipo;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public double getPrecio() {
        return precio;
    }

    public boolean isFichado() {
        return fichado;
    }

    public int getPuntosFantasy() {return puntosFantasy;}

    public void setPuntosFantasy(int puntosFantasy) {this.puntosFantasy = puntosFantasy;}

    public String getPais() {return pais;}

    public int getNumero() {return numero;}

    public String getPilotoExternalId() {return pilotoExternalId;}
}

