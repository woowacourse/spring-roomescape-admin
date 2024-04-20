package roomescape.dao;

import java.util.List;
import java.util.Optional;

public interface ReservationDao {
    List<Reservation> findAll();
    Optional<Reservation> findById(Long id);
    void save(Reservation reservation);
    void delete(Reservation reservation);
}
