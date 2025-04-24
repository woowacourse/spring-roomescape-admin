package roomescape.usecase;


import java.time.LocalTime;

public interface AddReservationTimeUseCase {
    CreateReservationTimeOutput addReservationTime(LocalTime startAt);
}
