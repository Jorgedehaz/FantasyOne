package max.fantasyone.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import max.fantasyone.model.Liga;
import max.fantasyone.model.Piloto;
import max.fantasyone.repository.PilotoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class PilotoInitializerService {

    private final ObjectMapper mapper;
    private final PilotoRepository pilotoRepo;

    public PilotoInitializerService(ObjectMapper mapper,
                                    PilotoRepository pilotoRepo) {
        this.mapper = mapper;
        this.pilotoRepo = pilotoRepo;
    }

    @Transactional
    public void inicializarParaLiga(Liga liga) {
        // 1) Crea el recurso sobre el classpath
        ClassPathResource resource = new ClassPathResource("pilotos.json");
        try (InputStream is = resource.getInputStream()) {
            // 2) Lee directamente la lista de Piloto
            List<Piloto> pilotos = mapper.readValue(
                    is, new TypeReference<List<Piloto>>() {}
            );
            // 3) Ajusta relación y persiste
            pilotos.forEach(p -> {
                p.setId(null);
                p.setLiga(liga);
                p.setMercado(null);
                p.setFichado(false);
            });
            pilotoRepo.saveAll(pilotos);
        } catch (IOException e) {
            throw new RuntimeException("Error cargando pilotos de prueba", e);
        }
    }
}

