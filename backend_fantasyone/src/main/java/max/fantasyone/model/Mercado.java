package max.fantasyone.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Mercado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "mercado_pilotos", joinColumns = @JoinColumn(name = "mercado_id"),
            inverseJoinColumns = @JoinColumn(name = "piloto_id"))
    private List<Piloto> pilotosDia = new ArrayList<>();

    public Mercado() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public List<Piloto> getPilotosDia() {
        return pilotosDia;
    }

    public void setPilotosDia(List<Piloto> pilotosDia) {
        this.pilotosDia = pilotosDia;
    }
}

