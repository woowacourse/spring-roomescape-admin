package roomescape.reservation;

import java.util.List;

public interface ReservationDao {
    Reservation add(Reservation reservation);

    List<Reservation> getAll();

    void deleteById(Long id);
}
