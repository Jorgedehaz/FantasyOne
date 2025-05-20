package max.fantasyone.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class EquipoUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "liga_id", nullable = false)
    private Liga liga;

    /**
     * Lista de pilotos fichados para este equipo.
     * Tamaño máximo 2 pilotos.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "equipo_pilotos",
            joinColumns = @JoinColumn(name = "equipo_id"),
            inverseJoinColumns = @JoinColumn(name = "piloto_id")
    )
    @Size(max = 2, message = "Un equipo no puede tener más de 2 pilotos")
    private List<Piloto> pilotos = new ArrayList<>();

    @Column(nullable = false)
    private double monedas;  // presupuesto restante

    @Column(nullable = false)
    private int puntosAcumulados = 0; // Inicializado en 0

    @Column(nullable = false, updatable = false)
    private LocalDateTime creacion = LocalDateTime.now(); // Fecha de creacion que no vamos a actualizar a posteriori

    public EquipoUsuario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Liga getLiga() {
        return liga;
    }

    public void setLiga(Liga liga) {
        this.liga = liga;
    }

    public List<Piloto> getPilotos() {
        return pilotos;
    }

    public void setPilotos(List<Piloto> pilotos) {
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

    public LocalDateTime getCreacion() {
        return creacion;
    }

    public void setCreacion(LocalDateTime creacion) {
        this.creacion = creacion;
    }

    public void ficharPiloto(Piloto p) {
        if (pilotos.size() >= 2) {
            throw new IllegalStateException("Ya tienes 2 pilotos fichados");
        }
        if (monedas < p.getPrecio()) {
            throw new IllegalStateException("Presupuesto insuficiente");
        }
        pilotos.add(p);
        monedas -= p.getPrecio();
    }
}
