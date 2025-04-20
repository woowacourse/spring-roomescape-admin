package roomescape;

import java.util.List;

public interface ReservationDao {
    List<Reservation> findAll();
    Reservation save(Reservation reservation);
    boolean removeById(long id);
}
