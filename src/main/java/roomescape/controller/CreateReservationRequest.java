package roomescape.controller;

import java.time.LocalDateTime;
import roomescape.domain.Reservation;
import roomescape.util.CustomDateTimeFormatter;

public record CreateReservationRequest(String date, String name, String time) {

    public Reservation to(final long id) {
        LocalDateTime localDateTime = CustomDateTimeFormatter.getLocalDateTime(this.date, this.time);
        return new Reservation(id, this.name, localDateTime);
    }
}
