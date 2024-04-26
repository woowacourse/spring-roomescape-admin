package roomescape.repository;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationRepository {
    void saveReservation(Reservation reservation);

    List<Reservation> findAllReservation();

    void deleteReservationById(long reservationId);
}
