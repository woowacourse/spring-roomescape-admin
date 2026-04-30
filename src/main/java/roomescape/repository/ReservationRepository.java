package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.domain.Reservation;

public interface ReservationRepository {

    List<Reservation> getAll();

    Reservation save(Reservation reservation);

    void deleteById(long reservationId);

    Optional<Reservation> findByReservationTimeId(long reservationTimeId);
}
