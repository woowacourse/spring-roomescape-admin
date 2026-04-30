package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationResponseDto(
        Long id,
        String name,
        LocalDate date,
        ReservationTime time
) {

    public static ReservationResponseDto from(Reservation reservation) {
        if (reservation == null) return null;

        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}
