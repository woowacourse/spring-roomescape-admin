package roomescape.repository;

import roomescape.domain.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    List<Reservation> findAll();

    long create(Reservation reservation, long reservationTimeId);

    boolean deleteById(long id);

    Optional<Reservation> findById(long id);

    boolean isExistByReservationTimeId(long reservationTimeId);
}
