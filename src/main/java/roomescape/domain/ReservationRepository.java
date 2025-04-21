package roomescape.domain;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository {

    long addReservation(Reservation reservation);

    List<Reservation> findAllReservations();

    void deleteReservation(Long id);
}
