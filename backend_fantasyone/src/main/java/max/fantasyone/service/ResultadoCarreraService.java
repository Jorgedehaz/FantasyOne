package max.fantasyone.service;

import max.fantasyone.model.ResultadoCarrera;
import max.fantasyone.repository.ResultadoCarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultadoCarreraService {

    private final ResultadoCarreraRepository resultadoRepository;

    @Autowired
    public ResultadoCarreraService(ResultadoCarreraRepository resultadoRepository) {
        this.resultadoRepository = resultadoRepository;
    }

    public List<ResultadoCarrera> obtenerTodos() {
        return resultadoRepository.findAll();
    }

    public ResultadoCarrera guardar(ResultadoCarrera resultado) {
        return resultadoRepository.save(resultado);
    }

    public List<ResultadoCarrera> buscarPorPiloto(Long pilotoId) {
        return resultadoRepository.findByPiloto_Id(pilotoId);
    }

    public Optional<ResultadoCarrera> buscarPorPilotoYCarrera(Long pilotoId, Long carreraId) {
        return resultadoRepository.findByPiloto_IdAndCarrera_Id(pilotoId, carreraId);
    }
}

