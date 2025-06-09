package max.fantasyone.service;

import max.fantasyone.dto.request.PilotoRequestDTO;
import max.fantasyone.model.Piloto;
import max.fantasyone.repository.PilotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PilotoService {

    private final PilotoRepository pilotoRepository;

    @Autowired
    public PilotoService(PilotoRepository pilotoRepository) {
        this.pilotoRepository = pilotoRepository;
    }

    public List<Piloto> obtenerTodos() {
        return pilotoRepository.findAll();
    }

    public Optional<Piloto> obtenerPorId(Long id) {
        return pilotoRepository.findById(id);
    }

    // Para un solo piloto desde entidad
    public Piloto guardar(Piloto piloto) {
        return pilotoRepository.save(piloto);
    }

    // Para varios pilotos desde DTO
    public Piloto guardar(PilotoRequestDTO dto) {
        Piloto piloto = new Piloto();
        piloto.setNombre(dto.getNombre());
        piloto.setApellido(dto.getApellido());
        piloto.setNombreCompleto(dto.getNombreCompleto());
        piloto.setAcronimo(dto.getAcronimo());
        piloto.setPais(dto.getPais());
        piloto.setEquipo(dto.getEquipo());
        piloto.setColorEquipo(dto.getColorEquipo());
        piloto.setNumero(dto.getNumero());
        piloto.setImagenUrl(dto.getImagenUrl());
        piloto.setFichado(dto.isFichado());
        piloto.setPrecio(dto.getPrecio());

        return pilotoRepository.save(piloto);
    }


    public void eliminar(Long id) {
        pilotoRepository.deleteById(id);
    }

    public List<Piloto> obtenerPilotosNoFichados() {
        return pilotoRepository.findByFichadoFalse();
    }
}

