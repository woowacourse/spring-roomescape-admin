package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.entity.Reservation;

public record ReservationResponseDto(
        Long id,
        String name,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        @JsonFormat(pattern = "HH:mm")
        LocalTime time
) {

    public static ReservationResponseDto toDto(final Reservation reservation) {
        return new ReservationResponseDto(reservation.getId(), reservation.getName(),
                reservation.getDate(),
                reservation.getTime());
    }
}
