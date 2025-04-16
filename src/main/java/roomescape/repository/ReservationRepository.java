package roomescape.repository;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationRepository {
    long save(final Reservation reservation);
    List<Reservation> findAll();
}
