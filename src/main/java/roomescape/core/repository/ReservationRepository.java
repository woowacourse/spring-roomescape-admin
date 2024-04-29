package roomescape.core.repository;

import java.util.List;
import roomescape.core.model.Reservation;

public interface ReservationRepository {

    Long createReservation(Reservation reservation);

    List<Reservation> readReservations();

    Reservation readReservationById(Long id);

    int deleteReservation(Long id);
}
