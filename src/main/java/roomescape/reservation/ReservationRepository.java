package roomescape.reservation;

import java.util.List;
import roomescape.dto.ReservationRequest;

public interface ReservationRepository {
    Reservation saveReservation(ReservationRequest wantToSaveReservationRequest);
    void deleteReservation(Long wantToDeleteId);
    List<Reservation> findAllReservations();
    boolean isExistReservation(ReservationRequest wantToSaveReservationRequest);
}
