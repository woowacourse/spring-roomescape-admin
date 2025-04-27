package roomescape.service.reservationtime.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.model.ReservationTime;

public record ReservationTimeResponse(
        Long id,
        @JsonFormat(pattern = "HH:mm")
        LocalTime startAt) {

    public static ReservationTimeResponse of(final ReservationTime time) {
        return new ReservationTimeResponse(time.getId(), time.getStartAt());
    }

    public static ReservationTimeResponse from(final Long id, final ReservationTime time) {
        return new ReservationTimeResponse(id, time.getStartAt());
    }

}
