package max.fantasyone.dto;

public class ApuestaVirualDTO {
    private Long id;
    private String tipo; // GANADOR, POLE, etc.
    private String piloto;
    private String carrera;
    private double monto;
    private boolean acertada;
    private double ganancia;
}