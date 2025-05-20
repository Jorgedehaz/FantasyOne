package max.fantasyone.service;

import max.fantasyone.dto.request.EquipoUsuarioRequestDTO;
import max.fantasyone.dto.response.EquipoUsuarioResponseDTO;
import max.fantasyone.mapper.EquipoUsuarioMapper;
import max.fantasyone.model.EquipoUsuario;
import max.fantasyone.model.Liga;
import max.fantasyone.model.Usuario;
import max.fantasyone.repository.LigaRepository;
import max.fantasyone.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LigaService {

    private final LigaRepository ligaRepository;
    private final UsuarioRepository usuarioRepository;
    private MercadoService mercadoService;
    private PilotoInitializerService piniService;
    private final EquipoUsuarioService equipoUsuarioService;
    private final EquipoUsuarioMapper equipoUsuarioMapper;

    // Si es necesario declaramos los repository necesarios y los incluimos en el constructor
    @Autowired
    public LigaService(LigaRepository ligaRepository, UsuarioRepository usuarioRepository, MercadoService mercadoService, PilotoInitializerService piniService,
                       EquipoUsuarioService equipoUsuarioService,
                       EquipoUsuarioMapper equipoUsuarioMapper) {
        this.ligaRepository = ligaRepository;
        this.usuarioRepository = usuarioRepository;
        this.mercadoService = mercadoService;
        this.piniService = piniService;
        this.equipoUsuarioService = equipoUsuarioService;
        this.equipoUsuarioMapper = equipoUsuarioMapper;
    }

    public List<Liga> obtenerTodas() {
        return ligaRepository.findAll();
    }

    public Optional<Liga> obtenerPorId(Long id) {
        return ligaRepository.findById(id);
    }

    public Optional<Liga> buscarPorNombre(String nombre) {
        return ligaRepository.findByNombre(nombre);
    }

    //Guardar una liga y su creador
    public Liga guardar(Liga liga, Long usuarioCreadorId) {
        Usuario usuario = usuarioRepository.findById(usuarioCreadorId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario creador no encontrado"));

        Liga ligaGuardada = ligaRepository.save(liga); // Primero se guarda la liga

        ligaGuardada.getUsuarios().add(usuario);
        usuario.getLigas().add(ligaGuardada);

        usuarioRepository.save(usuario); // Luego se guarda la relación con el usuario

        // Crear mercado automáticamente para esta liga
        piniService.inicializarParaLiga(ligaGuardada);
        mercadoService.generarMercadoInicial(ligaGuardada, 10);

        return ligaGuardada;
    }


    public void eliminar(Long id) {
        ligaRepository.deleteById(id);
    }

    //Obtener ligas privadas
    public List<Liga> obtenerPorPrivacidad(boolean privada) {
        return ligaRepository.findByPrivada(privada);
    }

    //unirse a una liga con la id de usuario
    public EquipoUsuarioResponseDTO unirseALiga(Long ligaId, Long usuarioId) {
        Liga liga = ligaRepository.findById(ligaId)
                .orElseThrow(() -> new IllegalArgumentException("Liga no encontrada"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (liga.getUsuarios().contains(usuario)) {
            throw new IllegalStateException("Ya estás unido a esta liga");
        }

        if (liga.getUsuarios().size() >= liga.getMaxUsuarios()) {
            throw new IllegalStateException("La liga ya está completa");
        }

        liga.getUsuarios().add(usuario);
        usuario.getLigas().add(liga);

        ligaRepository.save(liga); // guarda también la relación entre el usuario y la liga a la que se une
        usuarioRepository.save(usuario);

        // Crear equipo para el usuario en esta liga
        EquipoUsuarioRequestDTO dto = new EquipoUsuarioRequestDTO();
        dto.setUsuarioId(usuarioId);
        dto.setLigaId(ligaId);
        dto.setPilotoIds(Collections.emptyList());
        EquipoUsuario equipo = equipoUsuarioService.crearEquipo(dto);

        return equipoUsuarioMapper.toDTO(equipo);
    }

    //Unirse a una liga privada con clave de acceso
    public EquipoUsuarioResponseDTO unirseALigaPrivada(String nombreLiga, String claveAcceso, Long usuarioId) {
        Liga liga = ligaRepository.findByNombreAndCodigoAcceso(nombreLiga, claveAcceso)
                .orElseThrow(() -> new IllegalArgumentException("Nombre de liga o clave incorrectos"));
        // Reutiliza el método unirseALiga
        return unirseALiga(liga.getId(), usuarioId);
    }


}

