package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponseDto(
        int id,
        String name,
        LocalDate date,
        LocalTime time
) {

    public static ReservationResponseDto from(Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime());
    }
}
