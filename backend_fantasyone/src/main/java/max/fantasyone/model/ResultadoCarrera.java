package max.fantasyone.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ResultadoCarrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pilotoexternal_id", nullable = false)
    private String pilotoExternalId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carrera_id", nullable = false)
    private Carrera carrera;

    @Column(nullable = false)
    private LocalDateTime momento;  // nuevo: timestamp del resultado para medir a partir de cuando puntua para un equipo

    @Column(nullable = false)
    private int posicionFinal;

    @Column(nullable = false)
    private boolean vueltaRapida;

    @Column(nullable = false)
    private boolean polePosition;

    @Column(nullable = false)
    private boolean penalizado;

    @Column(nullable = false)
    private String tiempoTotal; // si da error pasar a Double en entity , dtos y mapper

    @Column(nullable = false)
    private int paradasBoxes;

    @Column(nullable = false)
    private int stints; //cantidad de vueltas dadas de forma continuada

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Neumatico tipoNeumatico; //  tipo de neumatico usado, ENUM con los campos de la API (revisar posibles errores)

    @Column(nullable = false)
    private int puntosFantasy; // hay que calcularlos con algoritmo

    public ResultadoCarrera() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPilotoExternalId() {return pilotoExternalId;}

    public void setPilotoExternalId(String pilotoExternalId) {this.pilotoExternalId = pilotoExternalId;}

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
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

    public Neumatico getTipoNeumatico() {
        return tipoNeumatico;
    }

    public void setTipoNeumatico(Neumatico tipoNeumatico) {
        this.tipoNeumatico = tipoNeumatico;
    }

    public int getPuntosFantasy() {
        return puntosFantasy;
    }

    public void setPuntosFantasy(int puntosFantasy) {
        this.puntosFantasy = puntosFantasy;
    }
}

