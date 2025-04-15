package roomescape.repository;

import java.util.List;
import roomescape.entity.Reservation;

public interface ReservationRepository {

    List<Reservation> getReservations();

    Reservation add(final Reservation entity);

    void deleteById(Long id);

    boolean existReservation(Long id);
}
