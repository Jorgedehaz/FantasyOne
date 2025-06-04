package max.fantasyone.mapper;


import max.fantasyone.dto.request.PilotoRequestDTO;
import max.fantasyone.dto.response.PilotoResponseDTO;
import max.fantasyone.model.Piloto;
import max.fantasyone.model.ResultadoCarrera;
import max.fantasyone.repository.ResultadoCarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PilotoMapper {


    private ResultadoCarreraRepository resultadoRepo;

    @Autowired
    public PilotoMapper(ResultadoCarreraRepository resultadoRepo) {
        this.resultadoRepo = resultadoRepo;
    }

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


    public PilotoResponseDTO toDTO(Piloto p) {
        // 1) Calcular “puntos acumulados hasta ahora”:
        int puntosAcumulados = resultadoRepo.sumPuntosByPilotoExternalId(p.getExternalId());

        // 2) Rellenar el DTO incluyendo esos puntos:
        return new PilotoResponseDTO(
                p.getId(),
                p.getNombreCompleto(),
                p.getEquipo(),
                p.getImagenUrl(),
                p.getPrecio(),
                p.isFichado(),
                p.getPais(),
                p.getNumero(),
                p.getExternalId(),
                puntosAcumulados
        );
    }
}
