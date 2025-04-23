package roomescape.reservation;

import java.time.LocalDate;
import roomescape.time.Time;
import roomescape.time.TimeResponse;

public record ReservationResponse(
        long id,
        String name,
        LocalDate date,
        TimeResponse time
) {

    public static ReservationResponse createResponse(final Reservation reservation, final Time time){
        return new ReservationResponse(
                reservation.id(),
                reservation.name(),
                reservation.date(),
                TimeResponse.createResponse(time)
        );
    }
}
