package roomescape.reservationtime.service.dto;

import java.time.format.DateTimeFormatter;
import roomescape.reservationtime.domain.ReservationTime;

public record ReservationTimeDto(Long id, String time) {

    public static ReservationTimeDto from(ReservationTime reservationTime) {
        return new ReservationTimeDto(
                reservationTime.getId(),
                reservationTime.getStartAt().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }
}
