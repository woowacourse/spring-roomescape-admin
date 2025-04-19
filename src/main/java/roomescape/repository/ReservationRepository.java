package roomescape.repository;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationRepository {

    List<Reservation> getReservations();

    Reservation add(final Reservation reservation);

    void deleteById(final Long id);

    boolean existReservation(final Long id);
}
