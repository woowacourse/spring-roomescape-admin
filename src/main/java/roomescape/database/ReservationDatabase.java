package roomescape.database;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationDatabase {

    List<Reservation> findAll();

    Reservation findById(Long id);

    Reservation add(Reservation reservation);

    void delete(Reservation reservation);
}
