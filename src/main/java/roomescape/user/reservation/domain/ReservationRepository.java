package roomescape.user.reservation.domain;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Long save(final Reservation reservation);

    Optional<Reservation> findById(final Long id);

    List<Reservation> findAll();

    void deleteById(final Long id);
}
