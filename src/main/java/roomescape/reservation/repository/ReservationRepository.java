package roomescape.reservation.repository;

import java.util.List;
import java.util.Optional;
import roomescape.reservation.model.Reservation;

public interface ReservationRepository {

    List<Reservation> findAll();

    Optional<Reservation> findById(Long id);

    Long save(Reservation reservation);

    void deleteById(Long id);
}
