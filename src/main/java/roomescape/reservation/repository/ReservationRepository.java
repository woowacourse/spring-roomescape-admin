package roomescape.reservation.repository;

import java.util.List;
import java.util.Optional;
import roomescape.reservation.domain.Reservation;

public interface ReservationRepository {

    long generateId();

    List<Reservation> findAll();

    Optional<Reservation> findById(Long id);

    Reservation save(Reservation reservation);

    void deleteAll();

    void deleteById(Long id);
}
