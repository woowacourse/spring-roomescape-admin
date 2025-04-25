package roomescape.usecase.ReservationTime;


import java.time.LocalTime;

public interface AddReservationTimeUseCase {
    ReservationTimeOutput addReservationTime(LocalTime startAt);
}
