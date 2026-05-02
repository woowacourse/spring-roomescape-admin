package roomescape.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.service.ReservationCommand;

@Repository
public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation findById(long id);

    long save(ReservationCommand reservationCommand);

    void deleteById(long id);
}
