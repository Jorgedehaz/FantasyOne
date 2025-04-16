package max.fantasyone.service;

import max.fantasyone.model.Liga;
import max.fantasyone.repository.LigaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LigaService {

    private final LigaRepository ligaRepository;

    @Autowired
    public LigaService(LigaRepository ligaRepository) {
        this.ligaRepository = ligaRepository;
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

    public Liga guardar(Liga liga) {
        return ligaRepository.save(liga);
    }

    public void eliminar(Long id) {
        ligaRepository.deleteById(id);
    }
}

