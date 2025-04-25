package roomescape.reservation;

import java.util.List;
import roomescape.dto.ReservationRequest;

public interface ReservationService {
    Reservation toReservation(ReservationRequest wantToSaveReservationRequest);
    Reservation saveReservation(ReservationRequest wantToSaveReservationRequest);
    void deleteReservation(Long wantToDeleteId);
    List<Reservation> findAllReservations();
    void validateSaveReservationAvailability(ReservationRequest wantToSaveReservationRequest);
    void validateDeleteReservationAvailability(Long wantToDeleteReservationId);
}
