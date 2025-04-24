package roomescape.reservationTime;

import java.util.List;

public interface ReservationTimeRepository {
    ReservationTime saveReservationTime(ReservationTime wantToSaveReservationTime);
    void deleteReservationTime(Long wantToDeleteId);
    List<ReservationTime> findAllReservationTimes();
    ReservationTime findById(Long wantToFindId);
}
