package roomescape.user.repository.reservation;

import java.util.List;
import java.util.Optional;
import roomescape.user.domain.Reservation;

public interface ReservationRepository {

    Long save(Reservation reservation);

    Optional<Reservation> findById(Long id);

    List<Reservation> findAll();

    void delete(Reservation reservation);
}
