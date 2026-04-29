package roomescape.domain;

import java.time.LocalDate;

public record Reservation(long id, String name, LocalDate date, ReservationTime time) {
    public boolean hasId(Long id) {
        return this.id == id;
    }
}
