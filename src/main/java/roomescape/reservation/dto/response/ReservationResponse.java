package roomescape.reservation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import roomescape.reservation.domain.Reservation;

public record ReservationResponse(Long id, String name,
                                  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
                                  LocalDate date, ReservationTimeResponse time) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(), reservation.getName(),
                reservation.getDate(), ReservationTimeResponse.from(reservation.getTime())
        );
    }
}
