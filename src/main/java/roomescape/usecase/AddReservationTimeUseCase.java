package roomescape.usecase;


import java.time.LocalTime;

public interface AddReservationTimeUseCase {
    ReservationTimeOutput addReservationTime(LocalTime startAt);
}
