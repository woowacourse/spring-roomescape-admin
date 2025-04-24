package roomescape.usecase;

import java.time.LocalTime;

public interface ReservationTimeRepository {
    ReservationTimeOutputModel addReservationTime(LocalTime startAt);
}
