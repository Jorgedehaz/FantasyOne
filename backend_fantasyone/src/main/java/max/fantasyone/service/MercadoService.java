package max.fantasyone.service;

import max.fantasyone.model.Mercado;
import max.fantasyone.repository.MercadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MercadoService {

    private final MercadoRepository mercadoRepository;

    @Autowired
    public MercadoService(MercadoRepository mercadoRepository) {
        this.mercadoRepository = mercadoRepository;
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
}

