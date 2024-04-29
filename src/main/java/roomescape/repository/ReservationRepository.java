package roomescape.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import roomescape.model.Reservation;

public interface ReservationRepository {

    Long createReservation(Reservation reservation);

    List<Reservation> readReservations();

    Reservation readReservationById(Long id);

    int deleteReservation(Long id);
}
