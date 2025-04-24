package roomescape.usecase;

import java.time.LocalTime;

public interface ReservationTimeRepository {
    CreateReservationTimeOutput addReservationTime(LocalTime startAt);
}
