package max.fantasyone.service;

import max.fantasyone.dto.request.PilotoMercadoRequestDTO;
import max.fantasyone.dto.response.PilotoMercadoResponseDTO;
import max.fantasyone.mapper.PilotoMercadoMapper;
import max.fantasyone.model.Mercado;
import max.fantasyone.model.Piloto;
import max.fantasyone.model.PilotoMercado;
import max.fantasyone.repository.MercadoRepository;
import max.fantasyone.repository.PilotoMercadoRepository;
import max.fantasyone.repository.PilotoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PilotoMercadoService {

    private final PilotoMercadoRepository repository;
    private final PilotoRepository pilotoRepository;
    private final MercadoRepository mercadoRepository;

    public PilotoMercadoService(PilotoMercadoRepository repository, PilotoRepository pilotoRepository, MercadoRepository mercadoRepository) {
        this.repository = repository;
        this.pilotoRepository = pilotoRepository;
        this.mercadoRepository = mercadoRepository;
    }

    public List<PilotoMercadoResponseDTO> obtenerPorMercado(Long mercadoId) {
        return repository.findByMercadoId(mercadoId).stream()
                .map(PilotoMercadoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PilotoMercadoResponseDTO crear(PilotoMercadoRequestDTO dto) {
        Piloto piloto = pilotoRepository.findById(dto.getPilotoId()).orElseThrow();
        Mercado mercado = mercadoRepository.findById(dto.getMercadoId()).orElseThrow();

        PilotoMercado entity = new PilotoMercado();
        entity.setPiloto(piloto);
        entity.setMercado(mercado);
        entity.setFichado(dto.isFichado());

        PilotoMercado saved = repository.save(entity);
        return PilotoMercadoMapper.toDTO(saved);
    }
}
