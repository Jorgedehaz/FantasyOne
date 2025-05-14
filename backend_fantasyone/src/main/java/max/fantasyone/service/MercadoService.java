package max.fantasyone.service;

import jakarta.transaction.Transactional;
import max.fantasyone.model.Liga;
import max.fantasyone.model.Mercado;
import max.fantasyone.model.Piloto;
import max.fantasyone.repository.MercadoRepository;
import max.fantasyone.repository.PilotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MercadoService {

    private final MercadoRepository mercadoRepository;
    private final PilotoRepository pilotoRepository;

    @Autowired
    public MercadoService(MercadoRepository mercadoRepository, PilotoRepository pilotoRepository) {
        this.mercadoRepository = mercadoRepository;
        this.pilotoRepository = pilotoRepository;
    }

    public List<Mercado> obtenerTodos() {
        return mercadoRepository.findAll();
    }

    public Optional<Mercado> obtenerPorId(Long id) {
        return mercadoRepository.findById(id);
    }

    public Optional<Mercado> buscarPorFecha(LocalDate fecha) {
        return mercadoRepository.findByFecha(fecha);
    }

    public Mercado guardar(Mercado mercado) {
        return mercadoRepository.save(mercado);
    }

    public void eliminar (Long id){
        mercadoRepository.deleteById(id);
    }


    //Nos va a dar la lista aleatoria de pilotos para el mercado , limitado a 10 por el controller
    @Transactional
    public List<Piloto> obtenerPilotosParaMercado(Long ligaId, int limit) {
        // 1) Traer todos los pilotos de esa liga
        List<Piloto> pool = pilotoRepository.findByLigaId(ligaId);
        // 2) Filtrar solo los que están libres
        List<Piloto> libres = pool.stream()
                .filter(p -> !p.isFichado())
                .collect(Collectors.toCollection(ArrayList::new));
        // 3) Mezclar y recortar al tamaño pedido
        Collections.shuffle(libres);

        return libres.stream().limit(limit).toList();
    }

     //Crea y guarda en BD el Mercado inicial para la liga:
     // - PilotosMercado = los primeros `limit` seleccionados
    @Transactional
    public Mercado generarMercadoInicial(Liga liga, int limit) {
        // crear mercado
        Mercado mercado = new Mercado();
        mercado.setFecha(LocalDate.now());
        mercado.setLiga(liga);

        List<Piloto> seleccionados = obtenerPilotosParaMercado(liga.getId(), limit);

        // Asignarles el mercado
        seleccionados.forEach(p -> p.setMercado(mercado));
        mercado.setPilotosMercado(seleccionados);

        return mercadoRepository.save(mercado);
    }


    public List<Piloto> obtenerPilotosPorLiga(Long ligaId) {
        Optional<Mercado> mercadoOpt = mercadoRepository.findByLigaId(ligaId);
        if (mercadoOpt.isEmpty()) {
            return List.of(); // lista vacía si no hay mercado
        }
        return pilotoRepository.findByMercadoId(mercadoOpt.get().getId());
    }

    public Optional<Mercado> obtenerPorLigaId(Long ligaId) {
        return mercadoRepository.findByLigaId(ligaId);
    }

}

