package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Reservation;

public record ReservationResponseDto(
        AtomicLong id,
        String name,
        LocalDate date,
        LocalTime time
) {

    public static ReservationResponseDto from(Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}
