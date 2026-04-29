package roomescape.reservation.payload;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationWithTimeResponse(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {

    public static ReservationWithTimeResponse of(Long id, String name, LocalDate date, LocalTime time) {
        return new ReservationWithTimeResponse(id, name, date, time);
    }

}
