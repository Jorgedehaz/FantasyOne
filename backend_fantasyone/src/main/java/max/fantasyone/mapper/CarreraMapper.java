package max.fantasyone.mapper;

import max.fantasyone.dto.request.CarreraRequestDTO;
import max.fantasyone.dto.response.CarreraResponseDTO;
import max.fantasyone.model.Carrera;
import org.springframework.stereotype.Component;

@Component
public class CarreraMapper {

    public Carrera toEntity(CarreraRequestDTO dto) {
        Carrera carrera = new Carrera();
        carrera.setNombreGP(dto.getNombreGP());
        carrera.setCircuito(dto.getCircuito());
        carrera.setFecha(dto.getFecha());
        carrera.setMeetingKey(dto.getMeetingKey());
        carrera.setTemporada(dto.getTemporada());
        carrera.setExternalId(dto.getExternalId());
        return carrera;
    }

    public CarreraResponseDTO toDTO(Carrera carrera) {
        return new CarreraResponseDTO(
                carrera.getId(),
                carrera.getNombreGP(),
                carrera.getCircuito(),
                carrera.getFecha(),
                carrera.getMeetingKey(),
                carrera.getTemporada(),
                carrera.getExternalId()
        );
    }
}
