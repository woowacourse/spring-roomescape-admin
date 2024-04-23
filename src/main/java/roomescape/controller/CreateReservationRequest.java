package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record CreateReservationRequest(LocalDate date, String name, LocalTime time) {

    public Reservation toDomain() {
        return new Reservation(
                null,
                this.name,
                this.date,
                this.time
        );
    }
}
