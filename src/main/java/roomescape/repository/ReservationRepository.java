package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.entity.Reservation;

public interface ReservationRepository {
    List<Reservation> readAll();

    Reservation save(Reservation reservation);

    Optional<Reservation> findById(long id);

    void deleteById(long id);
}
