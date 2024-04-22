package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;

public record ReservationTimeResponse (

        long id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        LocalTime startAt) {
    public static ReservationTimeResponse of(int index, ReservationTimeRequest reservationTimeRequest) {
        return new ReservationTimeResponse(index, reservationTimeRequest.startAt());
    }
}
