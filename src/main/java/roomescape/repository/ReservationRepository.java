package roomescape.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public interface ReservationRepository {
    List<Reservation> findAll();
    Optional<Reservation> findById(long id);
    Reservation save(Reservation reservation);
    void delete(long id);
}
