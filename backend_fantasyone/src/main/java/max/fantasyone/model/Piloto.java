package max.fantasyone.model;

import jakarta.persistence.*;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Piloto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id en la bd
                                   // Comentarios de ejemplo con lo que devuelve la API

    @Column(nullable = false)
    private String nombre;         // Max

    @Column(nullable = false)
    private String apellido;       // Verstappen

    @Column(nullable = false)
    private String nombreCompleto; // Max VERSTAPPEN

    @Column(nullable = false)
    private String acronimo;       // VER

    @Column(nullable = false)
    private int numero;            // 1 (driver_number)

    @Column(nullable = false)
    private String pais;           // NED

    @Column(nullable = false)
    private String equipo;         // Red Bull Racing

    @Column(nullable = false)
    private String colorEquipo;    // 3671C6

    @Column(nullable = false)
    private String imagenUrl;      // headshot_url

    @Column(nullable = false)
    private boolean fichado;       // si ya fue fichado por un jugador

    @Column(nullable = false)
    private double precio;         // precio actual

    @OneToMany(mappedBy = "piloto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResultadoCarrera> resultados = new ArrayList<>();

}
