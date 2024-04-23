package roomescape.repository;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationRepository {

    Reservation addReservation(Reservation reservation);

    List<Reservation> findAll();

    void deleteById(Long id);

    void deleteAll();
}
