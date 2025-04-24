package roomescape.reservation;

import java.util.List;
import roomescape.dto.ReservationRequest;

public interface ReservationRepository {
    Reservation saveReservation(Reservation wantToSaveReservation);
    void deleteReservation(Long wantToDeleteId);
    List<Reservation> findAllReservations();
    boolean isExistReservation(ReservationRequest wantToSaveReservationRequest);
}
