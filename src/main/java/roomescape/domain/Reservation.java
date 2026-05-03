package roomescape.domain;

import java.time.LocalDate;

public record Reservation(Long id, String name, LocalDate date, ReservationTime time) {
    public long timeId() {
        return time.id();
    }
}
