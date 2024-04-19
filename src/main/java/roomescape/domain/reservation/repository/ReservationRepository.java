package roomescape.domain.reservation.repository;

import java.util.List;
import java.util.Optional;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationDateTime;

public interface ReservationRepository {
    Reservation save(Reservation reservation);

    boolean existsByReservationDateTime(ReservationDateTime reservationDateTime);

    Optional<Reservation> findById(long id);

    List<Reservation> findAll();

    void deleteById(long id);
}
