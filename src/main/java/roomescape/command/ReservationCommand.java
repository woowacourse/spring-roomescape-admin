package roomescape.command;

import roomescape.domain.ReservationTime;

import java.time.LocalDate;

public record ReservationCommand(long id, String name, LocalDate date, ReservationTime time) {
    public boolean hasId(Long id) {
        return this.id == id;
    }

    public long timeId() {
        return time.id();
    }
}
