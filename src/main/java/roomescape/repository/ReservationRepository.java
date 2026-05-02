package roomescape.repository;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationRepository {

    List<Reservation> getAll();

    Reservation save(Reservation reservation);

    void deleteById(long reservationId);

    boolean existByReservationTimeId(long reservationTimeId);
}
