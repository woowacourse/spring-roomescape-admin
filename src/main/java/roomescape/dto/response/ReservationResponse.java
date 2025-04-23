package roomescape.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import roomescape.model.Reservation;

public record ReservationResponse(
    @JsonProperty("id")
    Long id,

    @JsonProperty("name")
    String name,

    @JsonProperty("date")
    LocalDate date,

    @JsonProperty("time")
    InnerReservationTime reservationTime
) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
            reservation.id(),
            reservation.name(),
            reservation.date(),
            new InnerReservationTime(
                reservation.id(),
                reservation.reservationTime().startAt()
            )
        );
    }

    private record InnerReservationTime(
        @JsonProperty("id")
        Long id,

        @JsonProperty("startAt")
        LocalTime startAt
    ) {
    }
}
