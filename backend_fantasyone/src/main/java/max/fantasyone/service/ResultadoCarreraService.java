package max.fantasyone.service;

import jakarta.persistence.EntityNotFoundException;
import max.fantasyone.dto.response.ResultadoCarreraResponseDTO;
import max.fantasyone.mapper.ResultadoCarreraMapper;
import max.fantasyone.model.Carrera;
import max.fantasyone.model.Piloto;
import max.fantasyone.model.ResultadoCarrera;
import max.fantasyone.repository.CarreraRepository;
import max.fantasyone.repository.PilotoRepository;
import max.fantasyone.repository.ResultadoCarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResultadoCarreraService {

    private final ResultadoCarreraRepository resultadoRepo;
    private final EquipoUsuarioService equipoService;
    private PilotoRepository pilotoRepo;
    private ResultadoCarreraMapper mapper;
    private CarreraRepository carreraRepo;

    @Autowired
    public ResultadoCarreraService(
            ResultadoCarreraRepository resultadoRepo,
            EquipoUsuarioService equipoService,
            PilotoRepository pilotoRepo,
            ResultadoCarreraMapper mapper,
            CarreraRepository carreraRepo) {
        this.resultadoRepo = resultadoRepo;
        this.equipoService = equipoService;
        this.pilotoRepo = pilotoRepo;
        this.mapper = mapper;
        this.carreraRepo = carreraRepo;
    }


    // Importa del API los resultados de la carrera, los transforma ResultadoCarrera y los guarda
   //@Transactional
   //public List<ResultadoCarreraResponseDTO> importarResultadosDeCarrera(Long carreraId) {
   //    // 1) Buscamos la entidad Carrera para sacar su externalId
   //    Carrera carrera = carreraRepo.findById(carreraId)
   //            .orElseThrow(() -> new EntityNotFoundException("Carrera no encontrada: " + carreraId));

   //    // 2) Hacemos la llamada a OpenF1Client pasando el externalId
   //    List<ResultadoCarrera> resultados = openF1Client.fetchResultados(carrera.getExternalId());

   //    // 3) Asociamos la Carrera y el Piloto correcto (por externalId) y guardamos
   //    resultados.forEach(r -> {
   //        r.setCarrera(carrera);

   //        Piloto piloto = pilotoRepo
   //                .findByExternalId(r.getPiloto().getExternalId())
   //                .orElseThrow(() -> new EntityNotFoundException(
   //                        "Piloto no encontrado (externalId): " + r.getPiloto().getExternalId())
   //                );
   //        r.setPiloto(piloto);

   //        // 4) Calculamos y seteamos puntos fantasy
   //        r.setPuntosFantasy(calcularPuntosFantasy(r));

   //        resultadoRepo.save(r);
   //    });

   //    // 5) Mapeamos a DTO de respuesta y devolvemos
   //    return resultados.stream()
   //            .map(mapper::toDTO)
   //            .collect(Collectors.toList());
   //}


    //Devuelve los resultados ya persistidos en BD de una carrera.
    @Transactional(readOnly = true)
    public List<ResultadoCarreraResponseDTO> obtenerResultadosPorCarrera(Long carreraId) {
        List<ResultadoCarrera> resultados = resultadoRepo.findByCarreraId(carreraId);
        return resultados.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    //Obtener todos los resultados
    public List<ResultadoCarrera> findAll() {
        return resultadoRepo.findAll();
    }


    // Algoritmo que asigna puntos fantasy según posición, vuelta rápida, pole, penalizaciones...
    public int calcularPuntosFantasy(ResultadoCarrera r) {
        int puntos = 0;

        switch (r.getPosicionFinal()) {
            case 1: puntos += 25; break;
            case 2: puntos += 18; break;
            case 3: puntos += 15; break;
            case 4: puntos += 12; break;
            case 5: puntos += 10; break;
            case 6: puntos += 8; break;
            case 7: puntos += 6; break;
            case 8: puntos += 4; break;
            case 9: puntos += 2; break;
        }
        if (r.isVueltaRapida())  puntos += 3;
        if (r.isPolePosition())  puntos += 3;
        if (r.isPenalizado())    puntos -= 5;
        // …otras reglas: paradas , neumaticos (?).
        return Math.max(puntos, 0);
    }

    // Actualiza los puntos de todos los equipos que tuvieran al piloto fichado en el momento del resultado con @Scheduled
    // El endpoint manual llama a este metodo tambien
    @Transactional
    public void calcularPuntosParaTodos() {
        List<ResultadoCarrera> todos = resultadoRepo.findAll();
        todos.forEach(r -> {
            r.setPuntosFantasy(calcularPuntosFantasy(r));
            resultadoRepo.save(r);
        });
    }

    //Calculo sólo el ÚLTIMO resultado de cada piloto, para que cada semana se sume solo el ultimo resultado
    @Transactional
    public void calcularPuntosUltimosPorPiloto() {
        // Agrupamos por pilotoExternalId y nos quedamos con el más reciente
        Map<String, ResultadoCarrera> ultimos = resultadoRepo.findAll().stream()
                .collect(Collectors.groupingBy(
                        ResultadoCarrera::getPilotoExternalId,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparing(ResultadoCarrera::getMomento)),
                                Optional::get
                        )
                ));
        ultimos.values().forEach(r -> {
            r.setPuntosFantasy(calcularPuntosFantasy(r));
            resultadoRepo.save(r);
        });
    }


    public void recalcularPuntosUltimoResultado() {
        List<String> pilotos = pilotoRepo.findAllExternalIds();
        pilotos.forEach(extId -> {
            ResultadoCarrera ultimo = resultadoRepo
                    .findTopByPilotoExternalIdOrderByMomentoDesc(extId);
            if (ultimo != null) {
                ultimo.setPuntosFantasy(calcularPuntosFantasy(ultimo));
                resultadoRepo.save(ultimo);
            }
        });
    }

    // Schedule para calcular los puntos del ultimo resultado los domingos 23:59
    // Se escoge esta hora porque lo más tarde que acaba una carrera en horario Madrid es a las 23:00-23:30
    @Scheduled(cron = "0 59 23 ? * SUN", zone = "Europe/Madrid")
    public void scheduledRecalculoSemanal() {
        calcularPuntosUltimosPorPiloto();
    }
}



