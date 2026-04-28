package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationInfo(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {
    public static ReservationInfo from(Reservation reservation) {
        return new ReservationInfo(
                null,
                reservation.getName(),
                reservation.getReservedDate(),
                reservation.getReservedTime()
        );
    }
}
