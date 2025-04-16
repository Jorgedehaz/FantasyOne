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

}

