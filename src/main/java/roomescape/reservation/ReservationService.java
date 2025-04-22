package roomescape.reservation;

import java.util.List;

public interface ReservationService {
    Reservation saveReservation(Reservation wantToSaveReservation);
    void deleteReservation(Long wantToDeleteId);
    Reservation findReservationById(Long wandToFindId);
    List<Reservation> findAllReservations();
    void validateReservationTimeAvailability(Reservation wantToSaveReservation);
}
