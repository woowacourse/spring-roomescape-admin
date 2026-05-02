package roomescape.domain;

import java.time.LocalDate;

public record Reservation(Long id, String name, LocalDate date, ReservationTime time) {
    public boolean hasId(Long id) {
        return this.id.equals(id);
    }

    public long timeId() {
        return time.id();
    }
}
