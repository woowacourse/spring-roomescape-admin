package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record ReservationResponseDto(Long id, String name, LocalDate date, LocalTime time) {

    public static ReservationResponseDto from(final Reservation reservation) {
        return new ReservationResponseDto(reservation.getId(), reservation.getName(), reservation.getDate(),
                reservation.getTime());
    }
}
