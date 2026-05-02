package roomescape.request;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.time.LocalDate;

public record ReservationCommand(String name, LocalDate date, Long timeId) {
    public Reservation toReservation(ReservationTime time) {
        return new Reservation(null, name, date, time);
    }

    public ReservationCommand toCommand() {
        return new roomescape.command.ReservationCommand(null, name, date, timeId);
    }
}
