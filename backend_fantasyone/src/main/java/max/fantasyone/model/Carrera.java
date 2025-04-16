package max.fantasyone.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreGP;      // Hay que ponerlo a mano (?)

    @Column(nullable = false)
    private LocalDate fecha;      //  fechas de los JSON de la API

    @Column(nullable = false)
    private String circuito;      // Nombre si se puede obtener

    @Column(nullable = false)
    private int temporada;

    @Column(nullable = false)
    private int meetingKey;       // Clave única por GP (viene en todos los datos)

    @OneToMany(mappedBy = "carrera", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResultadoCarrera> resultados = new ArrayList<>();

    public Carrera() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreGP() {
        return nombreGP;
    }

    public void setNombreGP(String nombreGP) {
        this.nombreGP = nombreGP;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getCircuito() {
        return circuito;
    }

    public void setCircuito(String circuito) {
        this.circuito = circuito;
    }

    public int getTemporada() {
        return temporada;
    }

    public void setTemporada(int temporada) {
        this.temporada = temporada;
    }

    public int getMeetingKey() {
        return meetingKey;
    }

    public void setMeetingKey(int meetingKey) {
        this.meetingKey = meetingKey;
    }

    public List<ResultadoCarrera> getResultados() {
        return resultados;
    }

    public void setResultados(List<ResultadoCarrera> resultados) {
        this.resultados = resultados;
    }
}

