package max.fantasyone.controller;

import max.fantasyone.dto.request.LoginRequestDTO;
import max.fantasyone.dto.request.RegistroRequestDTO;
import max.fantasyone.dto.request.UsuarioRequestDTO;
import max.fantasyone.dto.response.UsuarioResponseDTO;
import max.fantasyone.mapper.UsuarioMapper;
import max.fantasyone.model.Usuario;
import max.fantasyone.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioMapper usuarioMapper;

    // Registro
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistroRequestDTO dto) {
        try {
            Usuario nuevo = usuarioService.registrarUsuario(dto);
            return ResponseEntity.ok(usuarioMapper.toDTO(nuevo));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginDTO) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorEmail(loginDTO.getEmail());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Usuario no encontrado");
        }

        Usuario usuario = usuarioOpt.get();
        if (!usuario.getPassword().equals(loginDTO.getPassword())) {
            return ResponseEntity.status(401).body("Contraseña incorrecta");
        }

        return ResponseEntity.ok(usuarioMapper.toDTO(usuario));
    }

}

