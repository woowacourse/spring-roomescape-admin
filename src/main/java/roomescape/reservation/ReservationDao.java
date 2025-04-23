package roomescape.reservation;

import java.util.List;

public interface ReservationDao {
    Long saveReservation(Reservation reservation, Long timeId);
    List<Reservation> findAllReservation();
    Reservation findReservationById(Long id);
    void deleteReservationById(long id);
}
