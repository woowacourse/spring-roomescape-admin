package roomescape.repository;

import java.util.List;
import roomescape.domain.ReservationInfo;
import roomescape.entity.Reservation;

public interface ReservationRepository {

    List<Reservation> getReservations();

    Reservation add(final ReservationInfo reservationInfo);

    void deleteById(final Long id);

    boolean existReservation(final Long id);
}
