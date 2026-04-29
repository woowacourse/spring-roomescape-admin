package roomescape.reservation.dao;

import java.util.List;

public interface ReservationsDao {
    List<ReservationEntity> getReservations();
    Long saveReservation(ReservationEntity reservationEntity);
    void deleteReservationById(Long id);
}
