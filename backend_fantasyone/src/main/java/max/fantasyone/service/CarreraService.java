package max.fantasyone.service;

import max.fantasyone.model.Carrera;
import max.fantasyone.repository.CarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CarreraService {

    private final CarreraRepository carreraRepository;

    @Autowired
    public CarreraService(CarreraRepository carreraRepository) {
        this.carreraRepository = carreraRepository;
    }

    public List<Carrera> obtenerTodas() {
        return carreraRepository.findAll();
    }

    public Optional<Carrera> obtenerPorId(Long id) {
        return carreraRepository.findById(id);
    }

    public Carrera guardar(Carrera carrera) {
        return carreraRepository.save(carrera);
    }

    public Optional<Carrera> buscarPorMeetingKey(int meetingKey) {
        return carreraRepository.findByMeetingKey(meetingKey);
    }

    public void eliminar(Long id) {
        carreraRepository.deleteById(id);
    }

    public Optional<Carrera> buscarPorNombre (String nombre){
        return carreraRepository.findByNombreGP(nombre);
    }

    public List<Carrera> buscarPorFecha(LocalDate fecha) {
        return carreraRepository.findByFecha(fecha);
    }

}

