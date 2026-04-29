package roomescape.reservation.repository;

import java.util.List;

public interface ReservationsRepository {
    List<ReservationEntity> getReservations();
    ReservationEntity saveReservation(ReservationEntity entity);
    void deleteReservationById(Long id);
}
