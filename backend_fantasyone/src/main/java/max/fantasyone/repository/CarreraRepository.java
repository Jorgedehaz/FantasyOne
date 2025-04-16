package max.fantasyone.repository;

import max.fantasyone.model.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Long> {
    Optional<Carrera> findByMeetingKey(int meetingKey);
    Optional<Carrera> findByNombreGP(String nombreGP);
}

