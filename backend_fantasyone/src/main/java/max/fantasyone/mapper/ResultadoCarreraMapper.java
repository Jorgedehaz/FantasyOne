package max.fantasyone.mapper;

import max.fantasyone.dto.request.ResultadoCarreraRequestDTO;
import max.fantasyone.dto.response.ResultadoCarreraResponseDTO;
import max.fantasyone.model.Carrera;
import max.fantasyone.model.Piloto;
import max.fantasyone.model.ResultadoCarrera;
import max.fantasyone.model.Neumatico;
import max.fantasyone.repository.CarreraRepository;
import max.fantasyone.repository.PilotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResultadoCarreraMapper {

    @Autowired private PilotoRepository pilotoRepository;
    @Autowired private CarreraRepository carreraRepository;

    public ResultadoCarrera toEntity(ResultadoCarreraRequestDTO dto) {
        ResultadoCarrera resultado = new ResultadoCarrera();

        resultado.setPiloto(pilotoRepository.findById(dto.getPilotoId()).orElseThrow());
        resultado.setCarrera(carreraRepository.findById(dto.getCarreraId()).orElseThrow());
        resultado.setPosicionFinal(dto.getPosicionFinal());
        resultado.setVueltaRapida(dto.isVueltaRapida());
        resultado.setPolePosition(dto.isPolePosition());
        resultado.setPenalizado(dto.isPenalizado());
        resultado.setTiempoTotal(dto.getTiempoTotal());
        resultado.setParadasBoxes(dto.getParadasBoxes());
        resultado.setStints(dto.getStints());
        resultado.setTipoNeumatico(Neumatico.valueOf(dto.getTipoNeumatico()));
        resultado.setPuntosFantasy(dto.getPuntosFantasy());

        return resultado;
    }

    public ResultadoCarreraResponseDTO toDTO(ResultadoCarrera resultado) {
        return new ResultadoCarreraResponseDTO(
                resultado.getId(),
                resultado.getPiloto().getNombreCompleto(),
                resultado.getCarrera().getNombreGP(),
                resultado.getPosicionFinal(),
                resultado.isVueltaRapida(),
                resultado.isPolePosition(),
                resultado.isPenalizado(),
                resultado.getTiempoTotal(),
                resultado.getParadasBoxes(),
                resultado.getStints(),
                resultado.getTipoNeumatico().name(),
                resultado.getPuntosFantasy()
        );
    }
}

