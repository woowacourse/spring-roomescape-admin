package roomescape.reservation;

import java.util.List;
import roomescape.dto.ReservationRequest;

public interface ReservationService {
    Reservation saveReservation(ReservationRequest wantToSaveReservationRequest);
    void deleteReservation(Long wantToDeleteId);
    List<Reservation> findAllReservations();
    void validateReservationTimeAvailability(ReservationRequest wantToSaveReservationRequest);
}
