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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    @Transactional
    public void generarMercadoInicial(Liga liga) {
        List<Piloto> disponibles = pilotoRepository.findByFichadoFalse(); // asume que hay un campo "fichado"
        Collections.shuffle(disponibles);

        List<Piloto> seleccionados = disponibles.stream()
                .limit(20)
                .toList();

        Mercado mercado = new Mercado();
        mercado.setFecha(LocalDate.now());
        mercado.setLiga(liga);
        mercado = mercadoRepository.save(mercado); // guardamos el mercado antes de asignarlo

        for (Piloto piloto : seleccionados) {
            piloto.setMercado(mercado);   // nuevo campo en Piloto
            piloto.setLiga(liga);         // nuevo campo en Piloto
            piloto.setFichado(false);
            pilotoRepository.save(piloto);
        }
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

