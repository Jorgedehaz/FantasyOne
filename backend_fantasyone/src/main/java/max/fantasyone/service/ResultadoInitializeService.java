// src/main/java/max/fantasyone/service/ResultadoInitializeService.java
package max.fantasyone.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import max.fantasyone.dto.response.ResultadoCarreraJsonDTO;
import max.fantasyone.model.Carrera;
import max.fantasyone.model.ResultadoCarrera;
import max.fantasyone.model.Neumatico;
import max.fantasyone.repository.CarreraRepository;
import max.fantasyone.repository.ResultadoCarreraRepository;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResultadoInitializeService {

    private final ResultadoCarreraRepository resultadoRepo;
    private final CarreraRepository carreraRepo;
    private final ObjectMapper mapper;

    public ResultadoInitializeService(ResultadoCarreraRepository resultadoRepo,
                                      CarreraRepository carreraRepo,
                                      ObjectMapper mapper) {
        this.resultadoRepo = resultadoRepo;
        this.carreraRepo = carreraRepo;
        this.mapper = mapper;
    }

    @Transactional
    public List<ResultadoCarrera> importarDesdeJson() throws IOException {
        // 1) Abrir el JSON desde classpath
        InputStream is = getClass().getClassLoader().getResourceAsStream("resultados.json");
        if (is == null) {
            throw new FileNotFoundException("No se encontró resultados.json en el classpath");
        }

        // 2) Leer la lista de DTOs
        List<ResultadoCarreraJsonDTO> dtos = mapper.readValue(
                is,
                new TypeReference<List<ResultadoCarreraJsonDTO>>() {}
        );

        List<ResultadoCarrera> guardados = new ArrayList<>();
        for (ResultadoCarreraJsonDTO dto : dtos) {
            // 3) Buscar la Carrera por externalId
            Carrera carrera = carreraRepo
                    .findByExternalId(dto.getCarreraExternalId())
                    .orElseThrow(() ->
                            new EntityNotFoundException("Carrera no encontrada: " + dto.getCarreraExternalId())
                    );

            // 4) Mapear a entidad (sin buscar Piloto, guardamos el externalId)
            ResultadoCarrera r = new ResultadoCarrera();
            r.setPilotoExternalId(dto.getPilotoExternalId());
            r.setCarrera(carrera);
            r.setMomento(dto.getMomento());
            r.setPosicionFinal(dto.getPosicionFinal());
            r.setVueltaRapida(dto.isVueltaRapida());
            r.setPolePosition(dto.isPolePosition());
            r.setPenalizado(dto.isPenalizado());
            r.setTiempoTotal(dto.getTiempoTotal());
            r.setParadasBoxes(dto.getParadasBoxes());
            r.setStints(dto.getStints());
            r.setTipoNeumatico(Neumatico.valueOf(dto.getTipoNeumatico()));
            // puntosFantasy inicializados a 0.Se calcularán en el service con metodo y algoritmo
            r.setPuntosFantasy(dto.getPuntosFantasy());

            guardados.add(resultadoRepo.save(r));
        }

        return guardados;
    }
}

