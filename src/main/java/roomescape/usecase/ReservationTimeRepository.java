package roomescape.usecase;

import java.time.LocalTime;

public interface ReservationTimeRepository {
    void addReservationTime(LocalTime startAt);
}
