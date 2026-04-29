package roomescape.domain.reservation.repository;

import java.util.List;
import roomescape.domain.reservation.domain.Reservation;

public interface ReservationRepository {

    List<Reservation> findAllReservations();

    Reservation save(Reservation reservation);

    void deleteReservationById(Long id);
}
