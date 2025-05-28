package max.fantasyone.repository;

import max.fantasyone.model.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Long> {
    Optional<Carrera> findByMeetingKey(int meetingKey);
    Optional<Carrera> findByNombreGP(String nombreGP);
    List<Carrera> findByFecha(LocalDate momento);

    Optional<Carrera> findByExternalId(String externalId);
}

