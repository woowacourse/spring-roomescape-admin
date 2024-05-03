package roomescape.dao;

import roomescape.entity.Reservation;
import java.util.List;

public interface ReservationRepository {

    Reservation createReservation(Reservation reservation);

    List<Reservation> findReservations();

    void removeReservation(Long id);
}
