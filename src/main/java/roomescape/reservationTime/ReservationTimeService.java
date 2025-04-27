package roomescape.reservationTime;

import java.util.List;

public interface ReservationTimeService {
    ReservationTime savaReservationTime(ReservationTime wantToSaveReservationTime);
    void deleteReservation(Long wantToDeleteId);
    List<ReservationTime> findAllReservationTime();
    ReservationTime findById(Long timeId);
    void validateSaveReservationTimeAvailability(ReservationTime wantToValidateReservationTime);
    void validateDeleteReservationTimeAvailability(Long wantToValidateReservationTimeId);
}
