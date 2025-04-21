package max.fantasyone.dto.response;

public class UsuarioResponseDTO {
    private Long id;
    private String nombre;
    private String email;
    private String rol;
    private boolean activo;

    public UsuarioResponseDTO(Long id, String nombre, String email, String rol, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
        this.activo = activo;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getRol() {
        return rol;
    }

    public boolean isActivo() {
        return activo;
    }
}

