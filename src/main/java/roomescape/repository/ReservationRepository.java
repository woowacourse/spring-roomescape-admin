package roomescape.repository;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationRepository {

    Reservation add(Reservation reservation);

    void remove(Long id);

    List<Reservation> findAll();
}
