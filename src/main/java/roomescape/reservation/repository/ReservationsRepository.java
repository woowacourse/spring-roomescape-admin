package roomescape.reservation.repository;

import java.util.List;
import roomescape.reservation.repository.dto.Reservation;

public interface ReservationsRepository {
    List<Reservation> findAllReservationsWithTime();
    ReservationEntity saveReservation(ReservationEntity entity);
    void deleteReservationById(Long id);
}
