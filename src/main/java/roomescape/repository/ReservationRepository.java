package roomescape.repository;

import java.util.List;
import roomescape.model.Reservation;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation add(Reservation reservation);

    void removeById(int id);
}
