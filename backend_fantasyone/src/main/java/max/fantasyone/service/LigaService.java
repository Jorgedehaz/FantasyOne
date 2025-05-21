package max.fantasyone.service;

import jakarta.transaction.Transactional;
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
    @Transactional  // importante para que todo persista en la misma tx
    public Liga guardar(Liga liga, Long usuarioCreadorId) {
        // 1) guardas la liga y añades al usuario
        Usuario usuario = usuarioRepository.findById(usuarioCreadorId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario creador no encontrado"));
        Liga guardada = ligaRepository.save(liga);
        guardada.getUsuarios().add(usuario);
        usuario.getLigas().add(guardada);
        usuarioRepository.save(usuario);

        // 2) inicializas pilotos y mercado
        piniService.inicializarParaLiga(guardada);
        mercadoService.generarMercadoInicial(guardada, 10);

        // 3) creamos el equipo del creador de la liga ya que este nunca llama al metodo unirse, se une al crearla
        EquipoUsuarioRequestDTO dto = new EquipoUsuarioRequestDTO();
        dto.setUsuarioId(usuarioCreadorId);
        dto.setLigaId(guardada.getId());
        dto.setPilotoIds(Collections.emptyList());
        equipoUsuarioService.crearEquipo(dto);

        return guardada;
    }


    public void eliminar(Long id) {
        ligaRepository.deleteById(id);
    }

    //Obtener ligas privadas
    public List<Liga> obtenerPorPrivacidad(boolean privada) {
        return ligaRepository.findByPrivada(privada);
    }

    //unirse a una liga con la id de usuario
    @Transactional
    public EquipoUsuarioResponseDTO unirseALiga(Long ligaId, Long usuarioId) {
        Liga liga = ligaRepository.findById(ligaId)
                .orElseThrow(() -> new IllegalArgumentException("Liga no encontrada"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (liga.getUsuarios().contains(usuario)) {
            throw new IllegalStateException("Ya estás en esa liga");
        }
        if (liga.getUsuarios().size() >= liga.getMaxUsuarios()) {
            throw new IllegalStateException("La liga está completa");
        }

        // 1) Persiste la relación usuario–liga
        liga.getUsuarios().add(usuario);
        usuario.getLigas().add(liga);
        ligaRepository.save(liga);
        usuarioRepository.save(usuario);

        // 2) Crea y persiste el equipo con presupuesto inicial
        EquipoUsuarioRequestDTO dto = new EquipoUsuarioRequestDTO();
        dto.setUsuarioId(usuarioId);
        dto.setLigaId(ligaId);
        dto.setPilotoIds(Collections.emptyList());
        EquipoUsuario equipo = equipoUsuarioService.crearEquipo(dto);

        // 3) Mapea y devuelve el DTO
        return equipoUsuarioMapper.toDTO(equipo);
    }

    //Unirse a una liga privada con clave de acceso
    @Transactional
    public EquipoUsuarioResponseDTO unirseALigaPrivada(String nombreLiga, String claveAcceso, Long usuarioId) {
        Liga liga = ligaRepository.findByNombreAndCodigoAcceso(nombreLiga, claveAcceso)
                .orElseThrow(() -> new IllegalArgumentException("Nombre de liga o clave incorrectos"));
        return unirseALiga(liga.getId(), usuarioId);
    }


}

