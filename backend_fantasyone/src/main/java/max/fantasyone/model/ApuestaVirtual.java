package max.fantasyone.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ApuestaVirtual {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "liga_id", nullable = false)
    private Liga liga;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carrera_id", nullable = false)
    private Carrera carrera;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "piloto_id", nullable = false)
    private Piloto piloto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoApuesta tipo;

    @Column(nullable = false)
    private double apostado;

    @Column(nullable = false)
    private boolean acertada;

    @Column(nullable = false)
    private double ganado;

    @Column(nullable = false)
    private LocalDateTime fecha;

    public ApuestaVirtual() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Liga getLiga() {
        return liga;
    }

    public void setLiga(Liga liga) {
        this.liga = liga;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public boolean isAcertada() {
        return acertada;
    }

    public void setAcertada(boolean acertada) {
        this.acertada = acertada;
    }

    public Piloto getPiloto() {
        return piloto;
    }

    public void setPiloto(Piloto piloto) {
        this.piloto = piloto;
    }

    public TipoApuesta getTipo() {
        return tipo;
    }

    public void setTipo(TipoApuesta tipo) {
        this.tipo = tipo;
    }

    public double getApostado() {
        return apostado;
    }

    public void setApostado(double apostado) {
        this.apostado = apostado;
    }

    public double getGanado() {
        return ganado;
    }

    public void setGanado(double ganado) {
        this.ganado = ganado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
