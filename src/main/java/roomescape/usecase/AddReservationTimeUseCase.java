package roomescape.usecase;


import java.time.LocalTime;

public interface AddReservationTimeUseCase {
    ReservationTimeOutputModel addReservationTime(LocalTime startAt);
}
