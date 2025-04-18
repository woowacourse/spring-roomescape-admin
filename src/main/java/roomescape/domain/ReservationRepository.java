package roomescape.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository {

    List<Reservation> getAll();

    Reservation save(Reservation reservation);

    Optional<Reservation> findById(Long id);

    void remove(Long id);
}
