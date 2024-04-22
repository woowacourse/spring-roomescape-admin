package roomescape.dto;

import roomescape.domain.ClientName;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record SaveReservationRequest(LocalDate date, String name, LocalTime time) {
    public Reservation toReservation() {
        return new Reservation(
                new ClientName(name),
                date,
                time
        );
    }
}
