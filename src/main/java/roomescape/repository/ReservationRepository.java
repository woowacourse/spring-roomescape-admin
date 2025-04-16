package roomescape.repository;

import java.util.List;
import roomescape.domain.ReservationInfo;
import roomescape.entity.Reservation;

public interface ReservationRepository {

    List<Reservation> getReservations();

    Reservation add(final ReservationInfo reservationInfo);

    void deleteById(Long id);

    boolean existReservation(Long id);
}
