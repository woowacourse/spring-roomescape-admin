package roomescape.repository;

import java.util.List;
import roomescape.entity.Reservation;

public interface ReservationRepository {
    List<Reservation> readAll();

    Reservation save(Reservation reservation);

    Reservation findById(long id);

    void deleteById(long id);
}
