package roomescape.domain.reservation.repository;

import java.util.List;
import roomescape.domain.reservation.Reservation;

public interface ReservationRepository {
    Reservation save(Reservation reservation);

    List<Reservation> findAll();

    void deleteById(Long id);
}
