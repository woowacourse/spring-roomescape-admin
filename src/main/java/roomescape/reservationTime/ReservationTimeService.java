package roomescape.reservationTime;

import java.util.List;

public interface ReservationTimeService {
    ReservationTime savaReservation(ReservationTime wantToSaveReservationTime);
    void deleteReservation(Long wantToDeleteId);
    List<ReservationTime> findAllReservationTime();
}
