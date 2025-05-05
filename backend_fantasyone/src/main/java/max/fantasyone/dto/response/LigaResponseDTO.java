package max.fantasyone.dto.response;

import java.util.List;

public class LigaResponseDTO {
    private Long id;
    private String nombre;
    private boolean privada;
    private int maxUsuarios;
    private String codigoAcceso;
    private List<Long> usuariosIds; // uso lista porque asi puedo saber las ids y cantidad con el length()


    public LigaResponseDTO() {
    }

    public LigaResponseDTO(Long id, String nombre, boolean privada, int maxUsuarios, String codigoAcceso) {
        this.id = id;
        this.nombre = nombre;
        this.privada = privada;
        this.maxUsuarios = maxUsuarios;
        this.codigoAcceso = codigoAcceso;
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

    public int getMaxUsuarios() {
        return maxUsuarios;
    }

    public void setMaxUsuarios(int maxUsuarios) {
        this.maxUsuarios = maxUsuarios;
    }

    public String getCodigoAcceso() {
        return codigoAcceso;
    }

    public void setCodigoAcceso(String codigoAcceso) {
        this.codigoAcceso = codigoAcceso;
    }

    public List<Long> getUsuariosIds() {
        return usuariosIds;
    }

    public void setUsuariosIds(List<Long> usuariosIds) {
        this.usuariosIds = usuariosIds;
    }
}
