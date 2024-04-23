package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.model.ReservationTime;

public record ReservationTimeResponse(Long id, String startAt) {

    public static ReservationTimeResponse from(ReservationTime reservationTime) {
        return new ReservationTimeResponse(
            reservationTime.getId(),
            reservationTime.getStartAt().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }

    public static ReservationTimeResponse of(Long id, ReservationTimeRequest reservationTimeRequest) {
        return new ReservationTimeResponse(id, reservationTimeRequest.startAt());
    }
}
