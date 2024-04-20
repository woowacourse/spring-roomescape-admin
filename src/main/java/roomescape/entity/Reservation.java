package roomescape.entity;

import java.time.LocalDateTime;

public record Reservation(Long id, String name, LocalDateTime dateTime) {

    public Reservation assignId(Long id) {
        return new Reservation(id, name, dateTime);
    }
}
