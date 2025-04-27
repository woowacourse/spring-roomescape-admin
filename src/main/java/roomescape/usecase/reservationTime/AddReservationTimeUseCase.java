package roomescape.usecase.reservationTime;


import java.time.LocalTime;

public interface AddReservationTimeUseCase {
    ReservationTimeOutput addReservationTime(LocalTime startAt);
}
