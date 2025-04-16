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

}
