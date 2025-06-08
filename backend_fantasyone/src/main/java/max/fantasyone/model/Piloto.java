package max.fantasyone.model;

import jakarta.persistence.*;

import lombok.Data;

import java.time.LocalDateTime;
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

    @ManyToOne
    @JoinColumn(name = "liga_id")
    private Liga liga;

    @ManyToOne
    @JoinColumn(name = "mercado_id")
    private Mercado mercado;

    @Column(unique = false, nullable = false)
    private String externalId;

    @Column(nullable = false)
    private int puntosFantasy;

    @Column(unique=false, nullable= true)
    private LocalDateTime fechaFichaje;

    public Piloto() {
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getAcronimo() {
        return acronimo;
    }

    public void setAcronimo(String acronimo) {
        this.acronimo = acronimo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getColorEquipo() {
        return colorEquipo;
    }

    public void setColorEquipo(String colorEquipo) {
        this.colorEquipo = colorEquipo;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public boolean isFichado() {
        return fichado;
    }

    public void setFichado(boolean fichado) {
        this.fichado = fichado;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Liga getLiga() {
        return liga;
    }

    public void setLiga(Liga liga) {this.liga = liga;}

    public Mercado getMercado() {
        return mercado;
    }

    public void setMercado(Mercado mercado) {
        this.mercado = mercado;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public int getPuntosFantasy() {return puntosFantasy;}

    public void setPuntosFantasy(int puntosFantasy) {this.puntosFantasy = puntosFantasy;}

    public LocalDateTime getFechaFichaje() {return fechaFichaje;}

    public void setFechaFichaje(LocalDateTime fechaFichaje) {this.fechaFichaje = fechaFichaje;}
}
