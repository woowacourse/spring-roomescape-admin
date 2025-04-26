package roomescape.usecase.reservation;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationRepository {

    Reservation addReservation(Reservation reservation);

    List<ReservationOutput> getAllReservations();

    void deleteReservation(long id);
}
