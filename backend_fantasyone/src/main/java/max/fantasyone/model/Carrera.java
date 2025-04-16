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

}

