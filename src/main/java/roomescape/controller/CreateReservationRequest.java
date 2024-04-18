package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record CreateReservationRequest(LocalDate date, String name, LocalTime time) {

    public Reservation toDomain(final Long id) {
        return new Reservation(
                id,
                this.name,
                this.date,
                this.time
        );
    }
}
