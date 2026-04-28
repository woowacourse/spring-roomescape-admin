package roomescape.domain;

import java.time.LocalDateTime;

public record Reservation(long id, String name, LocalDateTime dateTime) {
    public boolean hasId(Long id) {
        return this.id == id;
    }
}
