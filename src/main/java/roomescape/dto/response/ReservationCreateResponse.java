package roomescape.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import roomescape.domain.Reservation;

public record ReservationCreateResponse(long id, String name, String date, String time) {
    @JsonIgnore
    public String getLocationHeaderValue() {
        return "/reservations/" + id;
    }

    public static ReservationCreateResponse from(long id, Reservation reservation) {
        return new ReservationCreateResponse(id, reservation.getName(), reservation.getDate(), reservation.getTimeAsString());
    }
}
