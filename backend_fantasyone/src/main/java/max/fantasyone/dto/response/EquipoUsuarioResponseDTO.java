package max.fantasyone.dto.response;

import java.util.List;

public class EquipoUsuarioResponseDTO {
    private Long id;
    private String nombreUsuario;
    private String nombreLiga;
    private List<PilotoResponseDTO> pilotos;
    private double monedas;
    private int puntosAcumulados;

    public EquipoUsuarioResponseDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreLiga() {
        return nombreLiga;
    }

    public void setNombreLiga(String nombreLiga) {
        this.nombreLiga = nombreLiga;
    }

    public List<PilotoResponseDTO> getPilotos() {
        return pilotos;
    }

    public void setPilotos(List<PilotoResponseDTO> pilotos) {
        this.pilotos = pilotos;
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

