package max.fantasyone.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ResultadoCarrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "piloto_id", nullable = false)
    private Piloto piloto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carrera_id", nullable = false)
    private Carrera carrera;

    @Column(nullable = false)
    private int posicionFinal;

    @Column(nullable = false)
    private boolean vueltaRapida;

    @Column(nullable = false)
    private boolean polePosition;

    @Column(nullable = false)
    private boolean penalizado;

    @Column(nullable = false)
    private double tiempoTotal;

    @Column(nullable = false)
    private int paradasBoxes;

    @Column(nullable = false)
    private int stints; //cantidad de vueltas dadas de forma continuada

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Neumatico tipoNeumatico; //  tipo de neumatico usado, ENUM con los campos de la API (revisar posibles errores)

    @Column(nullable = false)
    private int puntosFantasy; // hay que calcularlos con algoritmo
}

