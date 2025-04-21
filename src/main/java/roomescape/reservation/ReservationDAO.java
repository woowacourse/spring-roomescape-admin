package roomescape.reservation;

import java.util.List;

public interface ReservationDAO {
    List<Reservation> findAllReservation();
}
