package roomescape.repository;


import java.util.List;
import roomescape.data.vo.Reservation;

public interface ReservationRepository {
    long add(final Reservation reservation);
    List<Reservation> getAll();
    Reservation get(final long id);
    void remove(final long id);
}
