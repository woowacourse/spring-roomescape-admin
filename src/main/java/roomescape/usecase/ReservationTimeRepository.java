package roomescape.usecase;

import java.time.LocalTime;

public interface ReservationTimeRepository {
    ReservationTimeResponseDto addReservationTime(LocalTime startAt);
}
