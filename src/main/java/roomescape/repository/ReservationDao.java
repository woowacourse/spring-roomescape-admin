package roomescape.repository;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationDao {
    List<Reservation> findAll();
    Reservation save(Reservation reservation);
    void deleteById(Long id);
}
