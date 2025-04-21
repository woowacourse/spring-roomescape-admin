package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.domain.Reservation;

public interface ReservationRepository {

    List<Reservation> getReservations();

    Reservation add(final Reservation reservation);

    Optional<Reservation> findById(final Long id);

    void deleteById(final Long id);
}
