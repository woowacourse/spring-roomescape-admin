package roomescape.admin.reservation.dto.response;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import roomescape.admin.reservation.entity.ReservationTime;

public record ReservationTimeResponse(Long id, String startAt) {
    public static ReservationTimeResponse from(ReservationTime reservationTime) {
        LocalTime startAt = reservationTime.getStartAt();
        return new ReservationTimeResponse(
                reservationTime.getId(), startAt.format(DateTimeFormatter.ofPattern("HH:mm"))
        );

    }
}
