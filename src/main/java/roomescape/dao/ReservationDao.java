package roomescape.dao;

import java.util.List;

public interface ReservationDao {
    List<Reservation> findAll();
    Reservation findById(Long id);
    Reservation save(Reservation reservation);
    void delete(Reservation reservation);
}
