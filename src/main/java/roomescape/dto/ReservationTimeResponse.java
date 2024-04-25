package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import roomescape.domain.ReservationTime;

public record ReservationTimeResponse(Long id, LocalTime startAt) {

    public ReservationTimeResponse {
        Objects.requireNonNull(id);
        Objects.requireNonNull(startAt);
    }

    public static ReservationTimeResponse from(final ReservationTime reservationTime) {
        return new ReservationTimeResponse(reservationTime.getId(), reservationTime.getStartAt());
    }
}
