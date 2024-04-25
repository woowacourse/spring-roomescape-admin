package roomescape.repository;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationDao {
    List<Reservation> findAll();
    Reservation findById(Long id);
    Reservation save(Reservation reservation);
    void delete(Reservation reservation);
}
