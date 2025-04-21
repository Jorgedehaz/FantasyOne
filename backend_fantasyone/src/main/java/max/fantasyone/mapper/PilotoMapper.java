package max.fantasyone.mapper;


import max.fantasyone.dto.request.PilotoRequestDTO;
import max.fantasyone.dto.response.PilotoResponseDTO;
import max.fantasyone.model.Piloto;
import org.springframework.stereotype.Component;

@Component
public class PilotoMapper {

    public Piloto toEntity(PilotoRequestDTO dto) {
        Piloto piloto = new Piloto();
        piloto.setNombre(dto.getNombre());
        piloto.setApellido(dto.getApellido());
        piloto.setNombreCompleto(dto.getNombreCompleto());
        piloto.setAcronimo(dto.getAcronimo());
        piloto.setPais(dto.getPais());
        piloto.setColorEquipo(dto.getColorEquipo());
        piloto.setEquipo(dto.getEquipo());
        piloto.setNumero(dto.getNumero());
        piloto.setImagenUrl(dto.getImagenUrl());
        piloto.setFichado(dto.isFichado());
        piloto.setPrecio(dto.getPrecio());
        return piloto;
    }


    public PilotoResponseDTO toDTO(Piloto piloto) {
        return new PilotoResponseDTO(piloto);
    }
}
