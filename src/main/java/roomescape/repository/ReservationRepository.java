package roomescape.repository;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationRepository {
    Reservation createReservation(Reservation reservation);
    List<Reservation> readReservations();
    void deleteReservation(Long id);
}
