package roomescape.repository.reservation;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationRepository {

    List<Reservation> findAllReservations();

    Reservation insertReservation(Reservation reservation);

    void deleteReservationById(Long id);
}
