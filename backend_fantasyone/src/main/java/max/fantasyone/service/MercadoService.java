package max.fantasyone.service;

import jakarta.transaction.Transactional;
import max.fantasyone.model.Liga;
import max.fantasyone.model.Mercado;
import max.fantasyone.model.Piloto;
import max.fantasyone.model.PilotoMercado;
import max.fantasyone.repository.MercadoRepository;
import max.fantasyone.repository.PilotoMercadoRepository;
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
    private final PilotoMercadoRepository pilotoMercadoRepository;

    @Autowired
    public MercadoService(MercadoRepository mercadoRepository, PilotoRepository pilotoRepository, PilotoMercadoRepository pilotoMercadoRepository) {
        this.mercadoRepository = mercadoRepository;
        this.pilotoRepository = pilotoRepository;
        this.pilotoMercadoRepository = pilotoMercadoRepository;
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
        List<Piloto> disponibles = pilotoRepository.findByFichadoFalse();
        Collections.shuffle(disponibles);

        List<Piloto> seleccionados = disponibles.stream()
                .limit(20)
                .toList();

        Mercado mercado = new Mercado();
        mercado.setFecha(LocalDate.now());
        mercado.setLiga(liga);
        mercadoRepository.save(mercado);

        for (Piloto piloto : seleccionados) {
            PilotoMercado pm = new PilotoMercado();
            pm.setPiloto(piloto);
            pm.setMercado(mercado);
            pm.setFichado(false);
            pilotoMercadoRepository.save(pm);
        }
    }

    public Optional<Mercado> obtenerPorLigaId(Long ligaId) {
        return mercadoRepository.findByLigaId(ligaId);
    }

}

