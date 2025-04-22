package roomescape.reservation;

import java.util.List;

public interface ReservationRepository {
    Reservation saveReservation(Reservation wantToSaveReservation);
    void deleteReservation(Long wantToDeleteId);
    Reservation findReservationById(Long wandToFindId);
    List<Reservation> findAllReservations();
    boolean isExistReservation(Reservation wantToSaveReservation);
}
