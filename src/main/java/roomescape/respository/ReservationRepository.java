package roomescape.respository;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.Reservation;

@Repository
public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation add(Reservation reservation);

    Boolean exist(long id);

    void delete(long id);
}
