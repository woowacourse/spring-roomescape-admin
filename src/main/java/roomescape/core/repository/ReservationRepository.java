package roomescape.core.repository;

import java.util.List;
import java.util.Optional;
import roomescape.core.domain.Reservation;

public interface ReservationRepository {

    Optional<Reservation> findById(Long id);

    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    void delete(Reservation reservation);

    void deleteAll();

    Boolean existByTimeId(Long timeId);
}
