package roomescape.time.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import java.util.Objects;
import roomescape.time.ReservationTime;

public record ReservationTimeResponse(
        Long id,
        @JsonFormat(pattern = "HH:mm") LocalTime startAt
) {
        public ReservationTimeResponse {
                Objects.requireNonNull(id);
                Objects.requireNonNull(startAt);
        }

        public static ReservationTimeResponse createResponse(final ReservationTime reservationTime){
                return new ReservationTimeResponse(reservationTime.id(), reservationTime.startAt());
        }
}
