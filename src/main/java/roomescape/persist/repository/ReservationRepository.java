package roomescape.persist.repository;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationTime;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation add(Reservation reservation);

    void removeById(long id);

    boolean isReservationDateTimeTaken(ReservationDate reservationDate, ReservationTime reservationTime);
}
