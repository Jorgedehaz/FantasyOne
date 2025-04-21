package max.fantasyone.dto.response;

public class LigaResponseDTO {
    private final Long id;
    private final String nombre;
    private final boolean privada;

    public LigaResponseDTO(Long id, String nombre, boolean privada) {
        this.id = id;
        this.nombre = nombre;
        this.privada = privada;
    }

    public Long getId() {
        return id;
    }

    public boolean isPrivada() {
        return privada;
    }

    public String getNombre() {
        return nombre;
    }
}
