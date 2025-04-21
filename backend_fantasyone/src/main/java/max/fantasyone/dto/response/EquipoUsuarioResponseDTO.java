package max.fantasyone.dto.response;

public class EquipoUsuarioResponseDTO {
    private final Long id;
    private final String nombreUsuario;
    private final String nombreLiga;
    private final String nombrePiloto1;
    private final String nombrePiloto2;
    private final double monedas;
    private final int puntosAcumulados;

    public EquipoUsuarioResponseDTO(Long id, String nombreUsuario, String nombreLiga,
                                    String nombrePiloto1, String nombrePiloto2,
                                    double monedas, int puntosAcumulados) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.nombreLiga = nombreLiga;
        this.nombrePiloto1 = nombrePiloto1;
        this.nombrePiloto2 = nombrePiloto2;
        this.monedas = monedas;
        this.puntosAcumulados = puntosAcumulados;
    }

    public Long getId() {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getNombreLiga() {
        return nombreLiga;
    }

    public String getNombrePiloto1() {
        return nombrePiloto1;
    }

    public String getNombrePiloto2() {
        return nombrePiloto2;
    }

    public double getMonedas() {
        return monedas;
    }

    public int getPuntosAcumulados() {
        return puntosAcumulados;
    }
}

