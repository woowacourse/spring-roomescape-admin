package roomescape.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import roomescape.domain.Reservation;

public record ReservationCreateResponse(long id, String name, String date,
                                        ReservationTimeCreateResponse time) {
    @JsonIgnore
    public String getLocationHeaderValue() {
        return "/reservations/" + id;
    }

    public static ReservationCreateResponse from(long id, Reservation reservation) {
        final ReservationTimeCreateResponse reservationTimeCreateResponse = new ReservationTimeCreateResponse(reservation.getTime()
                                                                                                                         .getId(), reservation.getStartAtAsString());
        return new ReservationCreateResponse(id, reservation.getName(), reservation.getDate(), reservationTimeCreateResponse);
    }
}
