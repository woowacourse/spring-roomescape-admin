package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import roomescape.domain.Reservation;

public record ReservationResponse(
        long id,
        String name,
        String date,
        @JsonProperty("time") ReservationTimeResponse time) {
    public ReservationResponse(Reservation reservation) {
        this(reservation.getId(), reservation.getName(), reservation.getDate(), new ReservationTimeResponse(reservation.getReservationTime()));
    }
}
