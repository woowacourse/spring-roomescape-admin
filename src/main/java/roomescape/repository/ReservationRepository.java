package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.entity.Reservation;

public interface ReservationRepository {

    List<Reservation> findAll();

    Optional<Reservation> findById(Long id);

    Reservation save(Reservation reservation);

    void delete(Reservation reservation);
}
