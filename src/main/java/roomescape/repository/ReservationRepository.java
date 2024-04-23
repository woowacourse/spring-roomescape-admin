package roomescape.repository;


import java.util.List;
import roomescape.Reservation;

public interface ReservationRepository {
    long add(final Reservation reservation);
    List<Reservation> getAll();
    void remove(final long id);
}
