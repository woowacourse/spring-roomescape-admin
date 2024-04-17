package roomescape.controller;

import roomescape.domain.Reservation;
import roomescape.util.CustomDateTimeFormatter;

public record CreateReservationRequest(String date, String name, String time) {

    public Reservation to(final Long id) {
        return new Reservation(
                id,
                this.name,
                CustomDateTimeFormatter.getLocalDate(this.date),
                CustomDateTimeFormatter.getLocalTime(this.time)
        );
    }
}
