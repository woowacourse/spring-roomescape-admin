package roomescape.reservation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationTime;

public record ReservationResponse(Long id, String name,
                                  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
                                  LocalDate date, ReservationTime reservationTime) {

    public static ReservationResponse of(Long id, Reservation reservation, ReservationTime reservationTime) {
        return new ReservationResponse(id, reservation.getName(), reservation.getDate(), reservationTime);
    }

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(), reservation.getName(),
                reservation.getDate(), reservation.getTime()
        );
    }
}
