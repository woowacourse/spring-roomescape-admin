package roomescape.repository;


import java.util.List;
import roomescape.Reservation;

public interface ReservationRepository {
    long save(final Reservation reservation);
    List<Reservation> findAll();
    void delete(final long id);
}
