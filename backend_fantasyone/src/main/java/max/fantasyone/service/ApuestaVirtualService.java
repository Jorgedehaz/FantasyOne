package max.fantasyone.service;

import max.fantasyone.model.ApuestaVirtual;
import max.fantasyone.repository.ApuestaVirtualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApuestaVirtualService {

    private final ApuestaVirtualRepository apuestaRepository;

    @Autowired
    public ApuestaVirtualService(ApuestaVirtualRepository apuestaRepository) {
        this.apuestaRepository = apuestaRepository;
    }

    public Optional<ApuestaVirtual> obtenerPorId(Long id){
        return apuestaRepository.findById(id);
    }

    public List<ApuestaVirtual> obtenerTodas() {
        return apuestaRepository.findAll();
    }

    public ApuestaVirtual guardar(ApuestaVirtual apuesta) {
        return apuestaRepository.save(apuesta);
    }

    public void eliminar(Long id) {
        apuestaRepository.deleteById(id);
    }

    public List<ApuestaVirtual> buscarPorUsuario(Long usuarioId) {
        return apuestaRepository.findByUsuario_Id(usuarioId);
    }

    public List<ApuestaVirtual> buscarPorLiga(Long ligaId) {
        return apuestaRepository.findByLiga_Id(ligaId);
    }

    public List<ApuestaVirtual> buscarPorCarrera(Long carreraId) {
        return apuestaRepository.findByCarrera_Id(carreraId);
    }

    public List<ApuestaVirtual> buscarPorPiloto(Long id) {
        return apuestaRepository.findByPiloto_Id(id);
    }
}

