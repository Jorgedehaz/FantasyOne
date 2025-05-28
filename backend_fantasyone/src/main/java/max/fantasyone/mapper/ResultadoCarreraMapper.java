package max.fantasyone.mapper;

import jakarta.persistence.EntityNotFoundException;
import max.fantasyone.dto.response.ResultadoCarreraResponseDTO;
import max.fantasyone.model.Neumatico;
import max.fantasyone.model.ResultadoCarrera;
import max.fantasyone.repository.CarreraRepository;
import max.fantasyone.repository.LigaRepository;
import max.fantasyone.repository.PilotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResultadoCarreraMapper {

    private final PilotoRepository pilotoRepo;
    private final CarreraRepository carreraRepo;

    public ResultadoCarreraMapper(PilotoRepository pilotoRepo, CarreraRepository carreraRepo) {
        this.pilotoRepo = pilotoRepo;
        this.carreraRepo = carreraRepo;
    }

    public ResultadoCarreraResponseDTO toDTO(ResultadoCarrera r) {
        return new ResultadoCarreraResponseDTO(
                r.getId(),
                r.getPilotoExternalId(),
                r.getCarrera().getExternalId(),
                r.getMomento(),
                r.isVueltaRapida(),
                r.getPosicionFinal(),
                r.isPolePosition(),
                r.isPenalizado(),
                r.getTiempoTotal(),
                r.getParadasBoxes(),
                r.getStints(),
                r.getTipoNeumatico().name(),
                r.getPuntosFantasy()
        );
    }

    public ResultadoCarrera toEntity(ResultadoCarreraResponseDTO dto) {
        ResultadoCarrera e = new ResultadoCarrera();
        // Aquí deberás buscar el Piloto y la Carrera por externalID , ya que es la que viene dada desde la API y no la BD
        e.setPilotoExternalId(dto.getPilotoExternalId());
        e.setCarrera(carreraRepo.findByExternalId(dto.getCarreraExternalId())
                .orElseThrow(() -> new EntityNotFoundException("Carrera extId "+dto.getCarreraExternalId())));
        e.setMomento(dto.getMomento());
        e.setPosicionFinal(dto.getPosicionFinal());
        e.setVueltaRapida(dto.isVueltaRapida());
        e.setPolePosition(dto.isPolePosition());
        e.setPenalizado(dto.isPenalizado());
        e.setTiempoTotal(dto.getTiempoTotal());
        e.setParadasBoxes(dto.getParadasBoxes());
        e.setStints(dto.getStints());
        e.setTipoNeumatico(Neumatico.valueOf(dto.getTipoNeumatico()));
        e.setPuntosFantasy(dto.getPuntosFantasy());
        return e;
    }
}
