package max.fantasyone.dto.response;

import max.fantasyone.model.Piloto;

public class PilotoResponseDTO {

    private Long id;
    private String nombreCompleto;
    private String equipo;
    private String imagenUrl;
    private double precio;
    private boolean fichado;

    public PilotoResponseDTO(Piloto piloto) {
        this.id = piloto.getId();
        this.nombreCompleto = piloto.getNombreCompleto();
        this.equipo = piloto.getEquipo();
        this.imagenUrl = piloto.getImagenUrl();
        this.precio = piloto.getPrecio();
        this.fichado = piloto.isFichado();
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
}

