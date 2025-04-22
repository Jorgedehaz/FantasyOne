package max.fantasyone.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Liga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private boolean privada;

    @Column(nullable = false)
    private String codigoAcceso;

    @OneToMany(mappedBy = "liga", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EquipoUsuario> equipos = new ArrayList<>();

    @OneToMany(mappedBy = "liga", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApuestaVirtual> apuestas = new ArrayList<>();

    @OneToOne(mappedBy = "liga", cascade = CascadeType.ALL, orphanRemoval = true)
    private Mercado mercado;

    public Liga() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isPrivada() {
        return privada;
    }

    public void setPrivada(boolean privada) {
        this.privada = privada;
    }

    public String getCodigoAcceso() {
        return codigoAcceso;
    }

    public void setCodigoAcceso(String codigoAcceso) {
        this.codigoAcceso = codigoAcceso;
    }

    public List<EquipoUsuario> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<EquipoUsuario> equipos) {
        this.equipos = equipos;
    }

    public List<ApuestaVirtual> getApuestas() {
        return apuestas;
    }

    public void setApuestas(List<ApuestaVirtual> apuestas) {
        this.apuestas = apuestas;
    }

    public Mercado getMercado() {
        return mercado;
    }

    public void setMercado(Mercado mercado) {
        this.mercado = mercado;
    }
}

