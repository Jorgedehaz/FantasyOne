package max.fantasyone.dto.request;

public class LigaRequestDTO {
    private String nombre;
    private boolean privada;
    private String codigoAcceso;
    private int maxUsuarios;

    public LigaRequestDTO() {}

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

    public int getMaxUsuarios() {
        return maxUsuarios;
    }

    public void setMaxUsuarios(int maxUsuarios) {
        this.maxUsuarios = maxUsuarios;
    }
}
