package max.fantasyone.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class PilotoMercado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Piloto piloto; // piloto "base", no ligado a una liga

    @ManyToOne
    private Mercado mercado;

    private boolean fichado;
    private BigDecimal precio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Piloto getPiloto() {
        return piloto;
    }

    public void setPiloto(Piloto piloto) {
        this.piloto = piloto;
    }

    public Mercado getMercado() {
        return mercado;
    }

    public void setMercado(Mercado mercado) {
        this.mercado = mercado;
    }

    public boolean isFichado() {
        return fichado;
    }

    public void setFichado(boolean fichado) {
        this.fichado = fichado;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
}
