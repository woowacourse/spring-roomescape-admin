package roomescape.reservation.repository;

import java.util.List;
import roomescape.reservation.domain.Reservation;

public interface ReservationRepository {
    List<Reservation> getAll();

    long put(Reservation reservation);

    void deleteById(long id);
}
