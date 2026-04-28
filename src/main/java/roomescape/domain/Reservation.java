package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(long id, String name, LocalDate date, LocalTime time) {
    public boolean hasId(Long id) {
        return this.id == id;
    }
}
