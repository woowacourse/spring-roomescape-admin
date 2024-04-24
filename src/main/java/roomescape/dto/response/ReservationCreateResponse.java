package roomescape.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import roomescape.domain.Reservation;

import java.net.URI;

public record ReservationCreateResponse(long id, String name, String date,
                                        ReservationTimeCreateResponse time) {
    @JsonIgnore
    public URI getLocationHeaderValue() {
        return URI.create("/reservations/" + id);
    }

    public static ReservationCreateResponse from(long id, Reservation reservation) {
        final ReservationTimeCreateResponse reservationTimeCreateResponse = new ReservationTimeCreateResponse(reservation.getTime()
                                                                                                                         .getId(), reservation.getStartAtAsString());
        return new ReservationCreateResponse(id, reservation.getNameAsString(), reservation.getDateAsString(), reservationTimeCreateResponse);
    }
}
