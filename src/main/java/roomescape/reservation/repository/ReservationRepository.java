package roomescape.reservation.repository;

import java.time.LocalTime;
import java.util.List;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Repository;
import roomescape.reservation.entity.Reservation;

@Repository
public interface ReservationRepository {

    Reservation save(Reservation reservation);

    List<Reservation> findAll();

    void deleteById(long id);

    boolean existsById(long id);
}
