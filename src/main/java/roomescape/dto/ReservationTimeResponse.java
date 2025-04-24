package roomescape.dto;

import roomescape.entity.ReservationTime;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public record ReservationTimeResponse(Long id, String startAt) {

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm", Locale.KOREA);

    public static ReservationTimeResponse toDto(final ReservationTime reservationTime) {
        return new ReservationTimeResponse(
                 reservationTime.getId(),
                 reservationTime.getStartAt().format(TIME_FORMATTER)
         );
    }
}
