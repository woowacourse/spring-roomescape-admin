package roomescape.reservation.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.reservation.Reservation;

@Repository
public interface ReservationRepository {
    long save(Reservation reservation);

    List<Reservation> findAll();
}
