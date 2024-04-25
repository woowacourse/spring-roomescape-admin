package roomescape.domain.reservation.repository;

import java.util.List;
import roomescape.domain.reservation.Reservation;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation create(Reservation reservation);

    void delete(Long id);
}
