package roomescape.domain;

import java.util.List;

public interface ReservationRepository {

    long addReservation(Reservation reservation);

    List<Reservation> findAllReservations();

    void deleteReservation(Long id);
}
