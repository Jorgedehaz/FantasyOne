package max.fantasyone.model;

import jakarta.persistence.*;

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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "piloto1_id", nullable = false)
    private Piloto piloto1;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "piloto2_id", nullable = false)
    private Piloto piloto2;

    @Column(nullable = false)
    private double monedas;

    @Column(nullable = false)
    private int puntosAcumulados;

}
