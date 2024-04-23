package roomescape.domain.repository;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationRepository {

    List<Reservation> findAllReservations();

    Reservation createReservation(Reservation reservation);

    void deleteReservation(Long id);
}
