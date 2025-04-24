package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;

@Repository
public interface ReservationRepository {

    Reservations findAll();

    Reservation save(Reservation reservation);

    boolean deleteById(Long id);
}
