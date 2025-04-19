package roomescape.reservation.domain;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Optional<Reservation> findById(long id);

    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    void deleteById(long id);
}
