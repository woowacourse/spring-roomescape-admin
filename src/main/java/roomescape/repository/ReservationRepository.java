package roomescape.repository;


import java.util.List;
import roomescape.Reservation;

public interface ReservationRepository {
    void save(final Reservation reservation);
    List<Reservation> findAll();
    void delete(final long id);
}
